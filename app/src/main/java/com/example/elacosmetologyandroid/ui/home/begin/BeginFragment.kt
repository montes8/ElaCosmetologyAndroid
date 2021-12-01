package com.example.elacosmetologyandroid.ui.home.begin


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.elacosmetologyandroid.databinding.FragmentBeginBinding
import com.example.elacosmetologyandroid.extensions.setOnClickDelay
import com.example.elacosmetologyandroid.extensions.uiValidateVisibilityTwoView
import com.example.elacosmetologyandroid.extensions.validateVisibility
import com.example.elacosmetologyandroid.manager.UserTemporary
import com.example.elacosmetologyandroid.model.MusicGeneric
import com.example.elacosmetologyandroid.ui.AppViewModel
import com.example.elacosmetologyandroid.ui.BaseFragment
import com.example.elacosmetologyandroid.ui.BaseViewModel
import com.example.elacosmetologyandroid.ui.home.begin.adapter.BannerAdapter
import com.example.elacosmetologyandroid.ui.home.begin.adapter.NameMusicAdapter
import com.example.elacosmetologyandroid.ui.home.begin.movi.MovieActivity
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class BeginFragment : BaseFragment(){

    private val viewModel: AppViewModel by viewModel(clazz = AppViewModel::class)
    private lateinit var binding: FragmentBeginBinding

    private var videoRequest: ActivityResultLauncher<Intent>? = null
    private var adapterNameMusic = NameMusicAdapter()
    private var adapterBanner = BannerAdapter()
    private var lisMusic : List<MusicGeneric> = ArrayList()

    private var youTubePlayerObserver: YouTubePlayer? = null
    private var flagVideo = true
    private var flagVideoNext = true
    private var positionMovie = 0

    companion object {
        fun newInstance() = BeginFragment().apply {
            arguments = Bundle().apply {}
        }

    }

    override fun getMainView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentBeginBinding.inflate(inflater)
        return binding.root
    }

    override fun setUpView() {
        lifecycle.addObserver(binding.youtubeBegin)
        lisMusic =  UserTemporary.listMusic
        configAdapter()
        viewModel.loadBanner()
        movieActivityForResult()
        configVideo()
        configAction()
    }

    override fun setBundle() {}

    private fun configAction(){
        binding.imgZoom.setOnClickDelay {
            videoRequest?.launch(Intent(activity, MovieActivity::class.java))
        }
    }

    private fun configVideo(){
        UserTemporary.musicGeneric = lisMusic[positionMovie]
        binding.youtubeBegin.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayerObserver = youTubePlayer
                youTubePlayerObserver?.loadVideo(UserTemporary.musicGeneric.id, UserTemporary.musicGeneric.duration.toFloat())
                if (flagVideo)youTubePlayerObserver?.pause()
                  flagVideo = false
            }
            override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {
                super.onCurrentSecond(youTubePlayer, second)
                if (second.toInt() == UserTemporary.musicGeneric.durationTotal && positionMovie < lisMusic.size-1){
                    if (flagVideoNext)configPositionMovie()
                } else{
                    UserTemporary.musicGeneric.duration = second.toInt()
                }
            }

            override fun onVideoDuration(youTubePlayer: YouTubePlayer, duration: Float) {
                super.onVideoDuration(youTubePlayer, duration)
                UserTemporary.musicGeneric.durationTotal = duration.toInt()
            }
        })
    }

    private fun movieActivityForResult(){
        videoRequest = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK ) {
                youTubePlayerObserver?.loadVideo(UserTemporary.musicGeneric.id, UserTemporary.musicGeneric.duration.toFloat())
            }
        }
    }

    fun setStopVideo(){
        youTubePlayerObserver?.pause()
    }

    private fun configAdapter(){
        binding.adapterBanner = adapterBanner
        binding.adapterName = adapterNameMusic
        if (lisMusic.isNotEmpty()){
            adapterNameMusic.setPositionSelected(positionMovie)
            adapterNameMusic.parameterList = lisMusic
            adapterNameMusic.onClickMusic = {
                positionMovie = it
                onUpdateMusic(lisMusic[it])
            }
        }
    }

    private fun configPositionMovie(){
        positionMovie += 1
        flagVideoNext = false
        adapterNameMusic.setPositionSelected(positionMovie)
        onUpdateMusic(lisMusic[positionMovie])
        Handler(Looper.getMainLooper()).postDelayed({flagVideoNext=true }, 2000)
    }

    private fun onUpdateMusic(music : MusicGeneric){
        UserTemporary.musicGeneric = music
        UserTemporary.musicGeneric.duration = 0
        UserTemporary.musicGeneric.durationTotal = 0
        youTubePlayerObserver?.loadVideo(UserTemporary.musicGeneric.id, UserTemporary.musicGeneric.duration.toFloat())
    }


    override fun observeLiveData() {
        viewModel.successBannerLiveData.observe(this,{
            it?.apply {
                if (this.isNotEmpty()){adapterBanner.bannerList = this}
                binding.rvBanner.uiValidateVisibilityTwoView(true,binding.shimmerBanner)
                binding.txtNameWelcome.validateVisibility(UserTemporary.getUser()?.name?.isNotEmpty()==true)
                binding.txtNameWelcome.text = UserTemporary.getUser()?.name
            }
        })

        viewModel.errorLiveData.observe(this,{
            binding.rvBanner.uiValidateVisibilityTwoView(true,binding.shimmerBanner)
        })
    }

    override fun getViewModel(): BaseViewModel? = null

}