package com.example.elacosmetologyandroid.utils

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.provider.Settings.System.getString
import android.telephony.PhoneNumberUtils
import android.util.Base64
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.elacosmetologyandroid.R
import com.example.elacosmetologyandroid.component.edit.EditCustomLayout
import com.example.elacosmetologyandroid.extensions.isEmailValid
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.ByteArrayOutputStream

inline fun <reified T> getData(context: Context, fileName: String): T {
    val jsonData = Gson()
    val json = context.assets.open(fileName).bufferedReader().use {
        it.readText()
    }
    return jsonData.fromJson(json, object : TypeToken<T>() {}.type)
}

fun encodeImageConverter(bm: Bitmap): String {
    val byte= ByteArrayOutputStream()
    bm.compress(Bitmap.CompressFormat.JPEG, 100, byte)
    val b = byte.toByteArray()
    return Base64.encodeToString(b, Base64.DEFAULT)
}


fun validateEmail(email: EditCustomLayout): Boolean {
    if (!isEmailValid(email.uiText)) {
        email.uiErrorMessage = email.context.getString(R.string.error_email_format)
        return false
    }
    return true
}

private fun openWhatsApp(context: Context) {
    if (existWhatsAppInDevice(context) || existWhatsAppInDeviceBusiness(context)) {
        val phone =
            "+51940372359" // Aquí va el número de teléfono, no olvidar el código de pais al inicio
        val sendIntent = Intent(Intent.ACTION_SEND)
        if (existWhatsAppInDevice(context)) {
            sendIntent.component = ComponentName("com.whatsapp", "com.whatsapp.ContactPicker")
        } else {
            sendIntent.component = ComponentName("com.whatsapp.w4b", "com.whatsapp.ContactPicker")
        }
        sendIntent.type = "text/plain"
        sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(phone) + "@s.whatsapp.net")
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Prueba de mensaje")
        context.startActivity(sendIntent)
    } else {
        Toast.makeText(context, context.resources.getString(R.string.whatsapp_not_found), Toast.LENGTH_SHORT)
            .show()
    }
}



private fun existWhatsAppInDevice(context: Context): Boolean {
    return existApplicationInDevice(context, PACKAGE_APP_WHATS_APP_BUSINESS)
}

private fun existWhatsAppInDeviceBusiness(context: Context): Boolean {
    return existApplicationInDevice(context, PACKAGE_APP_WHATS_APP)
}

private fun existApplicationInDevice(context: Context, name: String): Boolean {
    val apps = context.packageManager.getInstalledPackages(0)
    for (app in apps) {
        if (app.packageName == name) {
            return true
        }
    }
    return false
}