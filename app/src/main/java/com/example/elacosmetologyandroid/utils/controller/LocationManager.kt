package com.example.elacosmetologyandroid.utils.controller

import android.content.Context
import android.location.LocationManager
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes

typealias OnLocationStatusListener = (LocationStatus) -> Unit

class LocationManager(
    private val activity: ComponentActivity? = null,
    private val fragment: Fragment? = null
) {
    private val locationSettingsRequest: LocationSettingsRequest?
    private var registryIntentGpsResult: ActivityResultLauncher<IntentSenderRequest>? = null
    private var onLocationStatusListener: OnLocationStatusListener = {}

    init {
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(LocationController.locationRequest)
        locationSettingsRequest = builder.build()
        builder.setAlwaysShow(true)
        configManager()
    }

    private fun configManager() {
        val contract = ActivityResultContracts.StartIntentSenderForResult()
        registryIntentGpsResult =
            activity?.registerForActivityResult(contract) {
                configActionsPopUp()
            } ?: fragment?.registerForActivityResult(contract) {
                configActionsPopUp()
            }
    }

    private fun configActionsPopUp() {
        if (isGPSEnable())
            this.onLocationStatusListener(LocationStatus.SUCCESS)
        else
            this.onLocationStatusListener(LocationStatus.ASKING_PERMISSION)
    }

    private fun fetchLocationManager() =
        fetchActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager

    private fun fetchActivity(): ComponentActivity {
        if (activity != null || fragment != null) {
            return activity ?: fragment?.activity as ComponentActivity
        } else
            error("An activity is required")
    }

    fun isGPSEnable(): Boolean =
        fetchLocationManager().isProviderEnabled(LocationManager.GPS_PROVIDER)


    fun enableGPS(onLocationStatusListener: OnLocationStatusListener) {
        this.onLocationStatusListener = onLocationStatusListener
        if (isGPSEnable())
            this.onLocationStatusListener(LocationStatus.SUCCESS)
        else
            LocationServices.getSettingsClient(fetchActivity())
                .checkLocationSettings(locationSettingsRequest)
                .addOnSuccessListener {
                    this.onLocationStatusListener(LocationStatus.SUCCESS)
                }
                .addOnFailureListener { e ->
                    when ((e as ApiException).statusCode) {
                        LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                            if (e is ResolvableApiException)
                                showDialogEnableGPS(e)
                            else
                                this.onLocationStatusListener(LocationStatus.ASKING_PERMISSION)
                        }
                        LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE ->
                            this.onLocationStatusListener(LocationStatus.ERROR)
                    }

                }
    }

    private fun showDialogEnableGPS(resolvableApiException: ResolvableApiException) {
        val intentSender = IntentSenderRequest.Builder(resolvableApiException.resolution).build()
        registryIntentGpsResult?.launch(intentSender)
    }
}

enum class LocationStatus {
    SUCCESS, ERROR, ASKING_PERMISSION
}