package com.example.elacosmetologyandroid.ui.admin.video

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.example.elacosmetologyandroid.databinding.FragmentVideoBinding
import com.example.elacosmetologyandroid.ui.BaseFragment
import com.example.elacosmetologyandroid.ui.BaseViewModel
import com.example.elacosmetologyandroid.ui.admin.ParametersActivity
import com.example.elacosmetologyandroid.ui.admin.ParametersViewModel
import com.example.elacosmetologyandroid.utils.EMPTY
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class VideoFragment : BaseFragment() {

    private val viewModel: ParametersViewModel by viewModel(clazz = ParametersViewModel::class)
    private lateinit var binding: FragmentVideoBinding
    private var youTubePlayerObserver: YouTubePlayer? = null

    companion object { fun newInstance() = VideoFragment() }

    override fun getMainView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentVideoBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun setUpView() {
        configVideo()
        configAction()
    }

    override fun setBundle() {
    }

    override fun observeLiveData() {
        viewModel.successSaveVideoLiveData.observe(this,{
            it?.apply {
                clearView()
                (activity as ParametersActivity).showSuccessDialog()
            }
        })

        viewModel.errorLiveData.observe(this,{
            binding.btnSaveVideo.isButtonLoading = false
            (activity as ParametersActivity).errorSnackBarSaveData()
        })
    }

    private fun configAction(){
        binding.btnTestVideo.setOnClickButtonDelayListener{
            if (binding.editIdVideo.uiText.isNotEmpty()){ youTubePlayerObserver?.loadVideo(binding.editIdVideo.uiText, 0f) }
        }
        binding.btnSaveVideo.setOnClickButtonDelayListener{viewModel.saveVideo(binding.btnSaveVideo)}
        binding.editIdVideo.uiEditCustomListener = {validateVideo()}
        binding.editAuthorVideo.uiEditCustomListener = {validateVideo()}
        binding.editNameVideo.uiEditCustomListener = {validateVideo()}
        binding.editDescriptionVideo.addTextChangedListener {validateVideo() }

    }

    private fun validateVideo(){
        viewModel.validateVideo(binding.editIdVideo,binding.editAuthorVideo,binding.editNameVideo,
            binding.editDescriptionVideo,binding.btnSaveVideo)
    }

    private fun configVideo(){
        binding.youtubeVideoTest.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayerObserver = youTubePlayer
            }
        })
    }

    private fun clearView(){
        binding.editIdVideo.uiText = EMPTY
        binding.editAuthorVideo.uiText = EMPTY
        binding.editDescriptionVideo.setText(EMPTY)

    }

    fun setStopVideo(){
        youTubePlayerObserver?.pause()
    }

    override fun getViewModel(): BaseViewModel = viewModel
}