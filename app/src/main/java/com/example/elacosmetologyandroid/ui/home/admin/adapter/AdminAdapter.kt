package com.example.elacosmetologyandroid.ui.home.admin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.elacosmetologyandroid.BR
import com.example.elacosmetologyandroid.databinding.RowAdminBinding
import com.example.elacosmetologyandroid.extensions.setOnClickDelay
import com.example.elacosmetologyandroid.model.ItemModel

class AdminAdapter(var onClickAdmin: ((ItemModel) -> Unit)? = null) :
    RecyclerView.Adapter<AdminAdapter.AdminViewHolder>() {

    var adminList: List<ItemModel> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminViewHolder {
        return AdminViewHolder(
            RowAdminBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AdminViewHolder, position: Int) {
        holder.bind(adminList[position])
    }

    override fun getItemCount(): Int = adminList.size



    inner class AdminViewHolder(private val binding: RowAdminBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(admin: ItemModel) {
            binding.setVariable(BR.admin, admin)
            binding.executePendingBindings()
            binding.ctlAdmin.setOnClickDelay { onClickAdmin?.invoke(admin) }
        }
    }
}