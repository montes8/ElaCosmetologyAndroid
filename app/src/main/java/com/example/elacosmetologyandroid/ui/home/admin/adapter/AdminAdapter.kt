package com.example.elacosmetologyandroid.ui.home.admin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.elacosmetologyandroid.BR
import com.example.elacosmetologyandroid.databinding.RowAdminBinding
import com.example.elacosmetologyandroid.extensions.setOnClickDelay
import com.example.elacosmetologyandroid.model.ModelGeneric

class AdminAdapter(var onClickAdmin: ((ModelGeneric) -> Unit)? = null) :
    RecyclerView.Adapter<AdminAdapter.AdminViewHolder>() {

    var adminList: List<ModelGeneric> = arrayListOf()
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
        fun bind(admin: ModelGeneric) {
            binding.setVariable(BR.admin, admin)
            binding.executePendingBindings()
            binding.ctlAdmin.setOnClickDelay { onClickAdmin?.invoke(admin) }
        }
    }
}