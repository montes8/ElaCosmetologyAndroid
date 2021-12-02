package com.example.elacosmetologyandroid.utils.controller

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Environment
import android.os.Parcelable
import android.provider.MediaStore
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.exifinterface.media.ExifInterface
import com.example.elacosmetologyandroid.R
import com.example.elacosmetologyandroid.manager.PermissionManager
import com.example.elacosmetologyandroid.utils.EMPTY
import java.io.*
import java.util.*
import kotlin.collections.ArrayList

class CameraController(
    private val context: ComponentActivity,
    private val namePath: String,
    private val listener: CameraControllerListener?
){

    private var pictureFileName = EMPTY
    private lateinit var picturePathTemp: String
    private lateinit var pictureNameTemp: String
    private var cameraRequest: ActivityResultLauncher<Intent>? = null

    private val permissionManager: PermissionManager =
        PermissionManager(context, onDeny = {
            listener?.onCameraPermissionDenied()
        })

    init {
        registerIntent()
    }


    fun doCamera(){
        permissionManager.requestPermissions(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) {
            var pictureFile: File? = null
            try {
                pictureFile = createPictureFile()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            if (pictureFile != null) {
                val pictureUri = FileProvider.getUriForFile(
                    context,
                    context.applicationContext.packageName,
                    pictureFile
                )
                chooseCameraOptions(
                    context as Activity, context.getString(R.string.Select_option),
                    pictureUri
                )
            }
        }

    }

    private fun registerIntent(){
        cameraRequest = context.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if(result.resultCode == Activity.RESULT_OK) {
                onActivityResult(result.resultCode, result.data) }
        }
    }

    fun onActivityResult(resultCode: Int, data: Intent?) {
        if(resultCode == Activity.RESULT_OK) {
            val externalFilesDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString()
            val storageDir = File(
                context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                "" + namePath
            )
            if(data == null) {
                putImageCamera(storageDir, externalFilesDir)
            } else {
                putImageIntoFolder(data, externalFilesDir, storageDir)
            }
        }
    }


    private fun putImageCamera(storageDir: File, externalFilesDir: String){
        val directory = File(storageDir.path)
        val files = directory.listFiles()
        if(files != null) {
            for (i in files.indices) {
                val imgFile = File(storageDir.path + "/" + files[i].name)
                if(imgFile.name == pictureNameTemp && imgFile.exists()) {
                    val myBitmap = rotateIfNeeded(reduceBitmapSize(imgFile), Uri.fromFile(imgFile))
                    try {
                        FileOutputStream(picturePathTemp).use { out -> myBitmap.compress(
                            Bitmap.CompressFormat.JPEG,
                            50,
                            out
                        ) }
                        val path = "$externalFilesDir/$namePath/$pictureFileName.jpg"
                        val file = File(path)
                        val imgGallery = BitmapFactory.decodeFile(file.absolutePath)
                        listener?.onGetImageCameraCompleted(
                            path, rotateIfNeeded(
                                imgGallery, Uri.fromFile(
                                    file
                                )
                            )
                        )
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    private fun putImageIntoFolder(data: Intent?, externalFilesDir: String, storageDir: File) {
        try {
            val calendar = Calendar.getInstance()
            val pictureFileName = calendar.timeInMillis.toString()
            val photoFile = File(storageDir.path + "/" + pictureFileName + ".jpg")
            val inputStream: InputStream? = data?.data?.let { context.contentResolver.openInputStream(
                it
            ) }
            val fOutputStream = FileOutputStream(photoFile)
            copyStream(inputStream, fOutputStream)
            fOutputStream.close()
            inputStream?.close()
            val bitmap = rotateIfNeeded(reduceBitmapSize(photoFile), Uri.fromFile(photoFile))
            FileOutputStream(photoFile).use { out -> bitmap.compress(
                Bitmap.CompressFormat.JPEG,
                50,
                out
            ) }
            val path = "$externalFilesDir/$namePath/$pictureFileName.jpg"
            val file = File(path)
            val imgGallery = BitmapFactory.decodeFile(file.absolutePath)

            listener?.onGetImageCameraCompleted(path, imgGallery)
        } catch (e: java.lang.Exception) {
            Log.d("PHOTO_2ND_ERROR", "onActivityResult: $e")
        }
    }

    @Throws(IOException::class)
    private fun copyStream(input: InputStream?, output: OutputStream?) {
        val buffer = ByteArray(1024)
        var bytesRead: Int
        do {
            input?.apply {
                bytesRead = this.read(buffer)
                if(bytesRead == -1)
                    return
                output?.write(buffer, 0, bytesRead)
            }
        } while (true)
    }

    private fun createPictureFile(): File {
        val calendar = Calendar.getInstance()
        pictureFileName = calendar.timeInMillis.toString()
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val picture = File("$storageDir/$namePath", "$pictureFileName.jpg")
        val newPath = File("$storageDir/$namePath")
        if(!newPath.exists()) {
            newPath.mkdirs()
        }
        picturePathTemp = picture.absolutePath
        pictureNameTemp = picture.name
        return picture
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun chooseCameraOptions(context: Activity, title: String, outputFileUri: Uri) {
        val cameraIntents = ArrayList<Intent>()
        val captureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val packageManager = context.packageManager
        val listCam = packageManager.queryIntentActivities(captureIntent, 0)
        for (res in listCam) {
            val intent = Intent(captureIntent)
            intent.component = ComponentName(res.activityInfo.packageName, res.activityInfo.name)
            intent.setPackage(res.activityInfo.packageName)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri)
            cameraIntents.add(intent)
        }
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        val chooserIntent = Intent.createChooser(galleryIntent, title)
        chooserIntent.putExtra(
            Intent.EXTRA_INITIAL_INTENTS,
            cameraIntents.toTypedArray<Parcelable>()
        )
        cameraRequest?.launch(chooserIntent)
    }


    private fun reduceBitmapSize(imageFilePath: File): Bitmap {
        val bmOptions = BitmapFactory.Options()
        bmOptions.inJustDecodeBounds = true
        BitmapFactory.decodeFile(imageFilePath.absolutePath, bmOptions)
        bmOptions.inSampleSize = calculateInSampleSize(bmOptions)
        bmOptions.inJustDecodeBounds = false
        return BitmapFactory.decodeFile(imageFilePath.absolutePath, bmOptions)
    }

    private fun calculateInSampleSize(bmOptions: BitmapFactory.Options): Int {
        val photoWidth = bmOptions.outWidth
        val photoHeight = bmOptions.outHeight
        var scaleFactor = 1
        if (photoWidth > 1000 || photoHeight > 1000) {
            val halfPhotoWidth = photoWidth / 2
            val halfPhotoHeight = photoHeight / 2
            while (halfPhotoWidth / scaleFactor >= 500 && halfPhotoHeight / scaleFactor >= 500) {
                scaleFactor *= 2
            }
        }
        return scaleFactor
    }

    private fun rotateIfNeeded(bitmap: Bitmap, uri: Uri) : Bitmap {
        val exitInt = context.contentResolver.openInputStream(uri)?.let { ExifInterface(it) }
        return when(exitInt?.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_NORMAL
        )) {
            ExifInterface.ORIENTATION_ROTATE_90 -> {
                rotateImages(bitmap, 90)
            }
            ExifInterface.ORIENTATION_ROTATE_180 -> {
                rotateImages(bitmap, 180)
            }
            ExifInterface.ORIENTATION_ROTATE_270 -> {
                rotateImages(bitmap, 270)
            }
            else -> {
                bitmap
            }
        }
    }

    private fun rotateImages(imageToOrient: Bitmap, degreesToRotate: Int): Bitmap {
        var result: Bitmap = imageToOrient
        try {
            if (degreesToRotate != 0) {
                val matrix = Matrix()
                matrix.setRotate(degreesToRotate.toFloat())
                result = Bitmap.createBitmap(
                    imageToOrient,
                    0,
                    0,
                    imageToOrient.width,
                    imageToOrient.height,
                    matrix,
                    true /*filter*/
                )
            }
        } catch (e: java.lang.Exception) {
            Log.d("TAGError", "Exception when trying to orient image", e)

        }
        return result
    }

    interface CameraControllerListener {
        fun onCameraPermissionDenied()
        fun onGetImageCameraCompleted(path: String, img: Bitmap)
    }
}