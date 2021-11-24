package com.example.elacosmetologyandroid.ui.home.begin


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import com.example.elacosmetologyandroid.databinding.FragmentBeginBinding
import com.example.elacosmetologyandroid.extensions.setOnClickDelay
import com.example.elacosmetologyandroid.ui.BaseFragment
import com.example.elacosmetologyandroid.ui.home.begin.movi.MovieActivity
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class BeginFragment : BaseFragment() {

    private lateinit var binding: FragmentBeginBinding

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
        binding.youtubeBegin.addYouTubePlayerListener(object : AbstractYouTubePlayerListener(){
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo("SRt0KAMCI4Q",0f)
                youTubePlayer.pause()
            }
        })

        binding.imgZoom.setOnClickDelay {
            MovieActivity.start(requireContext())
        }
    }


    override fun observeLiveData() {
    }

    override fun getErrorObservers(): ArrayList<MutableLiveData<Throwable>> = arrayListOf()

}