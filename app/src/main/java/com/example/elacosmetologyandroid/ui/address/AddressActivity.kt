package com.example.elacosmetologyandroid.ui.address

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.Location
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import androidx.databinding.DataBindingUtil
import com.example.elacosmetologyandroid.R
import com.example.elacosmetologyandroid.databinding.ActivityAddressBinding
import com.example.elacosmetologyandroid.extensions.showDialogGeneric
import com.example.elacosmetologyandroid.manager.PermissionManager
import com.example.elacosmetologyandroid.model.LocationModel
import com.example.elacosmetologyandroid.model.PlaceModel
import com.example.elacosmetologyandroid.ui.BaseActivity
import com.example.elacosmetologyandroid.ui.BaseViewModel
import com.example.elacosmetologyandroid.utils.DATA_ADDRESS_PLACE
import com.example.elacosmetologyandroid.utils.MAP_LIMA_LATITUDE
import com.example.elacosmetologyandroid.utils.MAP_LIMA_LONGITUDE
import com.example.elacosmetologyandroid.utils.controller.LocationController
import com.example.elacosmetologyandroid.utils.controller.LocationManager
import com.example.elacosmetologyandroid.utils.controller.LocationStatus
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.dialog_generic.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddressActivity : BaseActivity(),LocationController.LocationControllerListener,OnMapReadyCallback{

    private val addressMapViewModel: AddressMapViewModel by viewModel(clazz = AddressMapViewModel::class)

    private lateinit var binding: ActivityAddressBinding
    private lateinit var mGoogleMap: GoogleMap
    private lateinit var permissionManager: PermissionManager
    private var locationManager: LocationManager = LocationManager(this)
    private var locationController : LocationController? = null
    private var selectedPlace: PlaceModel? = null
    private var currentLocation: LatLng? = null
    private var mPosition: LocationModel = LocationModel(MAP_LIMA_LATITUDE, MAP_LIMA_LONGITUDE)

    companion object {

        fun start(activity: Activity) = Intent(activity, AddressActivity::class.java)
    }

    override fun getMainView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_address)
        binding.lifecycleOwner = this
    }

    override fun setUpView() {
       configLocationInit()
       setUpGoogleMaps()
       configAction()
    }

    private fun configAction(){
        binding.btnConfirmAddress.setOnClickButtonDelayListener{
            val intent = Intent()
            intent.putExtra(DATA_ADDRESS_PLACE, selectedPlace)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    override fun observeViewModel() {
        addressMapViewModel.placeLocationLiveData.observe(this) {
            it?.apply {
                binding.addressMapInput.uiText = it.name
                selectedPlace = this
            }

        }
    }

    private fun setUpGoogleMaps() {
        binding.addressMapView.let {
            it.onCreate(null)
            it.onResume()
            it.getMapAsync (this)
        }
    }

    private fun configLocationInit(){
        locationController = LocationController(this,this)
        permissionManager = PermissionManager(activity = this,
            onDeny = {
                openRationaleLocationPermission(locationManager.isGPSEnable())
            },
            onRationale = {
                openRationaleLocationPermission(true)
            })
        verifyPermissions()
    }

    override fun onMapReady(p0: GoogleMap) {
        p0.let {
            mGoogleMap = it
            var locationMap : LatLng? = null
            if (currentLocation != null){
                locationMap = LatLng(currentLocation?.latitude ?: 0.0, currentLocation?.longitude ?: 0.0)
            } else { locationMap =  LatLng(mPosition.latitude, mPosition.longitude) }
            mGoogleMap.uiSettings.isCompassEnabled = false
            changeCameraPosition(locationMap)
            setupMapListeners()
            addressMapViewModel.searchAddress(locationMap)
        }
    }


    private fun changeCameraPosition(place: LatLng) {
        val animatedPos: CameraUpdate = CameraUpdateFactory.newLatLngZoom(place, 20f)
        mGoogleMap.animateCamera(animatedPos)
    }

    private fun setupMapListeners() {
        mGoogleMap.setOnCameraIdleListener {
            addressMapViewModel.searchAddress(mGoogleMap.cameraPosition.target)
        }
    }

    private fun verifyPermissions() {
        permissionManager.requestPermission(
            permission = Manifest.permission.ACCESS_FINE_LOCATION,
            onGranted = { loadLocationManager() })
    }

    private fun loadLocationManager(){
        locationManager.enableGPS {
            Handler(Looper.getMainLooper()).postDelayed({
                when (it) {
                LocationStatus.SUCCESS -> { locationController?.connect()}
                LocationStatus.ASKING_PERMISSION -> { }
                LocationStatus.ERROR -> { }
            } }, 500)

        }
    }

    private fun openPermissionSettings(isRational: Boolean,context: Context) {
        if (!isRational)
            context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        else {
            val intent = Intent()
            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            val uri = Uri.fromParts("package", context.packageName, null)
            intent.data = uri
            context.startActivity(intent)
        }
    }

    private fun openRationaleLocationPermission(isRational: Boolean = false) {
        showDialogGeneric(false, imageVisibility = false,
            title = getString(R.string.do_not_have_permission_to_location), description =
            getString(R.string.base_location_description),btnTextAccepted =  getString(R.string.base_location_button)
        ) {
            openPermissionSettings(isRational, this@AddressActivity)
        }
    }

    @SuppressLint("MissingPermission")
    override fun onGetLocationCompleted(location: Location?) {
        location?.let {
            changeCameraPosition(LatLng(it.latitude,it.longitude))
            addressMapViewModel.searchAddress(LatLng(it.latitude,it.longitude))
            mGoogleMap.isMyLocationEnabled = true
            mGoogleMap.uiSettings.isMyLocationButtonEnabled = false
        }

    }


    override fun onGetLocationUpdate(location: Location) {

    }

    override fun getViewModel(): BaseViewModel? = null

    override fun getValidActionToolBar() = false



}