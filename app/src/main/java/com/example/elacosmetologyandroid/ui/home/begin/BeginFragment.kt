package com.example.elacosmetologyandroid.ui.home.begin


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.MutableLiveData
import com.example.elacosmetologyandroid.databinding.FragmentBeginBinding
import com.example.elacosmetologyandroid.extensions.setOnClickDelay
import com.example.elacosmetologyandroid.manager.UserTemporary
import com.example.elacosmetologyandroid.ui.BaseFragment
import com.example.elacosmetologyandroid.ui.BaseViewModel
import com.example.elacosmetologyandroid.ui.home.HomeActivity
import com.example.elacosmetologyandroid.ui.home.begin.movi.MovieActivity
import com.example.elacosmetologyandroid.utils.EMPTY
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker

class BeginFragment : BaseFragment(){

    private lateinit var binding: FragmentBeginBinding

    private var videoRequest: ActivityResultLauncher<Intent>? = null

    private var youTubePlayerObserver: YouTubePlayer? = null
    private var flagVideo = true

    companion object {
        fun newInstance() = BeginFragment().apply {
            arguments = Bundle().apply {
                //putInt(MENU_HOME_ID, menuId)
               // putParcelable(USER, user)
            }
        }

    }

    override fun getMainView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentBeginBinding.inflate(inflater)
        return binding.root
    }

    override fun setUpView() {
        lifecycle.addObserver(binding.youtubeBegin)
        smsActivityForResult()
        configVideo()
        configAction()
    }

    private fun configAction(){
        binding.imgZoom.setOnClickDelay {
            videoRequest?.launch(Intent(activity, MovieActivity::class.java))
        }
    }

    private fun configVideo(){
        binding.youtubeBegin.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayerObserver = youTubePlayer
                youTubePlayerObserver?.loadVideo("SRt0KAMCI4Q", UserTemporary.duration.toFloat())
                if (flagVideo)youTubePlayerObserver?.pause()
                  flagVideo = false
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

    private fun smsActivityForResult(){
        videoRequest = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK ) {
                youTubePlayerObserver?.loadVideo("SRt0KAMCI4Q", UserTemporary.duration.toFloat())
            }
        }
    }

    fun setStopVideo(){
        youTubePlayerObserver?.pause()
    }


    override fun observeLiveData() {}

    override fun getViewModel(): BaseViewModel? = null

}