package com.example.elacosmetologyandroid.ui.home.begin.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.elacosmetologyandroid.R
import com.example.elacosmetologyandroid.model.MusicGeneric
import com.example.elacosmetologyandroid.utils.FADE_DURATION
import kotlinx.android.synthetic.main.row_botton_generic.view.*

class NameMusicAdapter(var onClickMusic: ((MusicGeneric) -> Unit)? = null) :
    RecyclerView.Adapter<NameMusicAdapter.LyricsViewHolder>() {

    private var positionSelected = -1

    var parameterList: List<MusicGeneric> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LyricsViewHolder {
        return LyricsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_botton_generic,parent,false))
    }

    fun setPositionSelected(value:Int){ positionSelected = value}

    override fun onBindViewHolder(holder: LyricsViewHolder, position: Int) {
        val music = parameterList[position]
        initView(holder,music,position)
        animationItem(holder.itemView)
        holder.itemView.setOnClickListener {
            if (positionSelected != position){
                positionSelected = position
                onClickMusic?.invoke(music)
                notifyDataSetChanged()
            }
        }
    }

    private fun initView(holder: LyricsViewHolder,music : MusicGeneric,position: Int){
        holder.itemView.txtCustomTitle.text = music.title
        holder.itemView.txtCustomTitle.background = getBackgroundLyrics(positionSelected == position,holder.itemView.context)
        holder.itemView.txtCustomTitle.setTextColor( getColorLyrics (positionSelected == position,holder.itemView.context))
    }

    private fun getBackgroundLyrics(value:Boolean,context: Context): Drawable? {
        return if (value)ContextCompat.getDrawable(context,
            R.drawable.bg_row_button) else ContextCompat.getDrawable(context, R.drawable.bg_row_botton_unselected)
    }

    private fun getColorLyrics(value:Boolean,context: Context): Int {
        return if (value)ContextCompat.getColor(context,
            R.color.white) else ContextCompat.getColor(context, R.color.pink_200)
    }

    override fun getItemCount(): Int = parameterList.size

    private fun animationItem(view: View){
        val anim = ScaleAnimation(0.0f,1.0f,0.0f,1.0f,Animation.RELATIVE_TO_SELF,0.5f,
            Animation.RELATIVE_TO_SELF,0.5f)
        anim.duration = FADE_DURATION
        view.startAnimation(anim)
    }

    class LyricsViewHolder(itemView : View): RecyclerView.ViewHolder(itemView)
}