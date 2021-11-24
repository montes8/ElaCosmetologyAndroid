package com.example.elacosmetologyandroid.ui.home.begin.movi

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import androidx.core.view.WindowCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import com.example.elacosmetologyandroid.R
import com.example.elacosmetologyandroid.databinding.ActivityMovieBinding
import com.example.elacosmetologyandroid.extensions.setOnClickDelay
import com.example.elacosmetologyandroid.manager.UserTemporary
import com.example.elacosmetologyandroid.ui.BaseActivity
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class MovieActivity : BaseActivity() {

    private lateinit var binding: ActivityMovieBinding


    companion object {
        fun start(context: Context) { context.startActivity(Intent(context, MovieActivity::class.java)) }
    }

    override fun getMainView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie)
        binding.lifecycleOwner = this
    }

    override fun setUpView() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = Color.TRANSPARENT
        configMovie()
        configAction()

    }

    private fun configMovie(){
        lifecycle.addObserver(binding.youtubeBeginFull)
        binding.youtubeBeginFull.addYouTubePlayerListener(object : AbstractYouTubePlayerListener(){
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo("SRt0KAMCI4Q", UserTemporary.duration.toFloat())
            }
        })
    }

    private fun configAction(){
        binding.imgZoomFull.setOnClickDelay {

        }

    }

    override fun observeLiveData() {

    }

    override fun getErrorObservers(): ArrayList<MutableLiveData<Throwable>> = arrayListOf()
}