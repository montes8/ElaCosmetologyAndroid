package com.example.elacosmetologyandroid.ui.home.begin.movi

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import com.example.elacosmetologyandroid.R
import com.example.elacosmetologyandroid.databinding.ActivityMovieBinding
import com.example.elacosmetologyandroid.manager.UserTemporary
import com.example.elacosmetologyandroid.ui.BaseActivity
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener


class MovieActivity : BaseActivity() {

    private lateinit var binding: ActivityMovieBinding

    override fun getMainView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie)
        binding.lifecycleOwner = this
    }

    override fun setUpView() {
        configFullScreen()
        configMovie()
    }

    private fun configMovie(){
        lifecycle.addObserver(binding.youtubeBeginFull)
        binding.youtubeBeginFull.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo("SRt0KAMCI4Q", UserTemporary.duration.toFloat())
            }

            override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {
                super.onCurrentSecond(youTubePlayer, second)
                UserTemporary.duration = if (second.toInt() == UserTemporary.durationTotal)0 else second.toInt()
            }

            override fun onVideoDuration(youTubePlayer: YouTubePlayer, duration: Float) {
                super.onVideoDuration(youTubePlayer, duration)
                UserTemporary.durationTotal = duration.toInt()
            }
        })
    }

    override fun onBackPressed() {
        finishFullScreen()
    }

    private fun finishFullScreen(){
        binding.youtubeBeginFull.release()
        setResult(Activity.RESULT_OK)
        finish()
    }

    private fun configFullScreen(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            @Suppress("DEPRECATION")
            window.decorView.apply {
                systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
            }
        }
    }

    override fun observeLiveData() {

    }

    override fun getErrorObservers(): ArrayList<MutableLiveData<Throwable>> = arrayListOf()
}