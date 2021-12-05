package com.example.elacosmetologyandroid.adapterview

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.elacosmetologyandroid.extensions.loadImageUrlPicasso
import com.example.elacosmetologyandroid.extensions.setImageString

@BindingAdapter("srcUlrPicasso")
fun setImagePicasso(imageView: ImageView, url: String?) {
    url?.let { loadImageUrlPicasso(url,imageView) }
}


@BindingAdapter("srcDrawableString")
fun setImageDrawableString(imageView: ImageView, nameImage: String?) {
    nameImage?.let { imageView.setImageDrawable(setImageString(nameImage,imageView.context)) }
}
