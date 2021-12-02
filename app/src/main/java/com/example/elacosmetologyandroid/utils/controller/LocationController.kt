package com.example.elacosmetologyandroid.utils.controller

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

class LocationController (
    private val context: Context,
    locationManagerListener: LocationControllerListener
) {

    private var fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    private var locationManagerListener: LocationControllerListener? = null
    private var lastLocation: Location? = null

    companion object {
        val locationRequest: LocationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    init {
        this.locationManagerListener = locationManagerListener
    }


    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            locationResult ?: return
            if (lastLocation == null){
                locationManagerListener.onGetLocationUpdate(locationResult.lastLocation)
            }
            for (location in locationResult.locations) {
                locationManagerListener.onGetLocationUpdate(location)
            }
        }
    }

    fun connect() {
        validateLocationPermission()
    }

    fun disconnect() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }




    @SuppressLint("MissingPermission")
    private fun validateLocationPermission() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.also {
                    locationManagerListener?.onGetLocationCompleted(it)
                }
            }

        startLocationUpdates()
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            null
        )
    }

    interface LocationControllerListener {
        fun onGetLocationCompleted(location: Location)
        fun onGetLocationUpdate(location: Location)
    }
}