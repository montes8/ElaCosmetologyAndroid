package com.example.elacosmetologyandroid.adapterview

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.elacosmetologyandroid.extensions.loadImageUrlPicasso

@BindingAdapter("srcUlrPicasso")
fun setImagePicasso(imageView: ImageView, url: String?) {
    url?.let { imageView.loadImageUrlPicasso(url) }
}
