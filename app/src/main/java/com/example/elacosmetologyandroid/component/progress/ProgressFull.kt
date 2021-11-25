package com.example.elacosmetologyandroid.component.progress

import android.content.Context

object ProgressFull {

    var progressBar : CrossProgressBarFull? = null

    fun showCrossProgressFull(context: Context){
        progressBar = CrossProgressBarFull(context, android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen)
        progressBar?.apply {
            this.show()
        }
    }


    fun hideCrossProgressFull(){
        progressBar?.apply {
            this.dismiss()
            progressBar = null
        }
    }
}