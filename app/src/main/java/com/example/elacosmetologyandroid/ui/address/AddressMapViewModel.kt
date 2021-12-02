package com.example.elacosmetologyandroid.ui.address

import android.content.Context
import android.content.res.Resources
import android.location.Geocoder
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.elacosmetologyandroid.model.LocationModel
import com.example.elacosmetologyandroid.model.PlaceModel
import com.example.elacosmetologyandroid.ui.BaseViewModel
import com.google.android.gms.maps.model.LatLng
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.*

class AddressMapViewModel : BaseViewModel(), KoinComponent {
    private val context: Context by inject()
    private val _placeLocationLiveData: MutableLiveData<PlaceModel> by lazy { MutableLiveData<PlaceModel>() }
    val placeLocationLiveData: LiveData<PlaceModel> get() = _placeLocationLiveData


     fun searchAddress(position: LatLng) {
        executeSuspend {
            val geoCoder = Geocoder(context, Locale("es"))
            val listAddress = geoCoder.getFromLocation(position.latitude, position.longitude, 1)
            listAddress?.isNullOrEmpty() ?: throw Resources.NotFoundException()
            val place = listAddress.first()
            val address = StringBuilder("").apply {
                for (i in 0..place.maxAddressLineIndex) {
                    append(place.getAddressLine(i))
                }
            }
            val selectedPlace = PlaceModel(
                name = address.toString().trim(),
                location = LocationModel(place.latitude, place.longitude),
                district = place.locality ?: "",
                province = place.adminArea ?: ""
            )
            _placeLocationLiveData.postValue(selectedPlace)
        }
    }
}