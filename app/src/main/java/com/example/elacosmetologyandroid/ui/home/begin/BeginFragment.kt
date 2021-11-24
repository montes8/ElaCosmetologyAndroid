package com.example.elacosmetologyandroid.ui.home.begin


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import com.example.elacosmetologyandroid.databinding.FragmentBeginBinding
import com.example.elacosmetologyandroid.extensions.setOnClickDelay
import com.example.elacosmetologyandroid.manager.UserTemporary
import com.example.elacosmetologyandroid.ui.BaseFragment
import com.example.elacosmetologyandroid.ui.home.HomeActivity
import com.example.elacosmetologyandroid.ui.home.begin.movi.MovieActivity
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker

class BeginFragment : BaseFragment(){

    private lateinit var binding: FragmentBeginBinding

    private var youTubePlayerObserver: YouTubePlayer? = null
    private var duracion : Int = 0

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
        configVideo()
        configAction()
    }

    private fun configAction(){
        binding.imgZoom.setOnClickDelay {
            MovieActivity.start(requireContext())
        }
    }

    private fun configVideo(){
        lifecycle.addObserver(binding.youtubeBegin)
        binding.youtubeBegin.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayerObserver = youTubePlayer
                youTubePlayerObserver?.loadVideo("SRt0KAMCI4Q", 0f)
                youTubePlayerObserver?.pause()
            }

            override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {
                super.onCurrentSecond(youTubePlayer, second)
                UserTemporary.duration = second.toInt()
            }
        })
    }

    fun setStopVideo(){
        youTubePlayerObserver?.pause()
    }


    override fun observeLiveData() {}

    override fun getErrorObservers(): ArrayList<MutableLiveData<Throwable>> = arrayListOf()

}