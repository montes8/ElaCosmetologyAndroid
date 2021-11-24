package com.example.elacosmetologyandroid.ui.home.begin.movi

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import com.example.elacosmetologyandroid.R
import com.example.elacosmetologyandroid.databinding.ActivityMovieBinding
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
        lifecycle.addObserver(binding.youtubeBeginFull)
        binding.youtubeBeginFull.addYouTubePlayerListener(object : AbstractYouTubePlayerListener(){
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo("SRt0KAMCI4Q",0f)
            }
        })
    }

    override fun observeLiveData() {

    }

    override fun getErrorObservers(): ArrayList<MutableLiveData<Throwable>> = arrayListOf()
}