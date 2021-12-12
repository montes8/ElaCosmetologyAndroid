package com.example.elacosmetologyandroid.ui.home.begin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.elacosmetologyandroid.BR
import com.example.elacosmetologyandroid.databinding.RowBannerBinding
import com.example.elacosmetologyandroid.extensions.setOnClickDelay
import com.example.elacosmetologyandroid.extensions.urlCustomImage
import com.example.elacosmetologyandroid.model.BannerModel
import com.example.elacosmetologyandroid.utils.TYPE_BANNER

class BannerAdapter(var onClickBanner: ((BannerModel) -> Unit)? = null) :
    RecyclerView.Adapter<BannerAdapter.BannerViewHolder>() {

    var bannerList: List<BannerModel> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        return BannerViewHolder(RowBannerBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        holder.bind(bannerList[position])
    }

    override fun getItemCount(): Int = bannerList.size



    inner class BannerViewHolder(private val binding: RowBannerBinding): RecyclerView.ViewHolder(binding.root){
        fun bind( banner: BannerModel) {
            binding.setVariable(BR.banner, banner)
            binding.executePendingBindings()
            binding.imgRowBanner.urlCustomImage(TYPE_BANNER,banner.id)
            binding.ctlBanner.setOnClickDelay { onClickBanner?.invoke(banner) }
        }
    }
}