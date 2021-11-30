package com.example.elacosmetologyandroid.ui.home.begin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.elacosmetologyandroid.BR
import com.example.elacosmetologyandroid.databinding.RowBannerBinding
import com.example.elacosmetologyandroid.model.ModelGeneric

class BannerAdapter(var onClickBanner: ((ModelGeneric) -> Unit)? = null) :
    RecyclerView.Adapter<BannerAdapter.BannerViewHolder>() {

    var bannerList: List<ModelGeneric> = arrayListOf()
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



    class BannerViewHolder(private val binding: RowBannerBinding): RecyclerView.ViewHolder(binding.root){
        fun bind( banner: ModelGeneric) {
            binding.setVariable(BR.banner, banner)
            binding.executePendingBindings()
        }
    }
}