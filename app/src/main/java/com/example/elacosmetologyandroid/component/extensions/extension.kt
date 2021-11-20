package com.example.elacosmetologyandroid.component.extensions

import android.view.View

fun View.visible() = apply {
    visibility = View.VISIBLE
}

fun View.gone() = apply {
    visibility = View.GONE
}