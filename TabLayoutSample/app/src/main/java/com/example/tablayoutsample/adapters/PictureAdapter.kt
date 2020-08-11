package com.example.tablayoutsample.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tablayoutsample.R
import com.example.tablayoutsample.databinding.PagerItemPictureBinding

/**
 * ページャーに画像を出力する
 */
class PictureAdapter(private val pictures: List<Uri>) : RecyclerView.Adapter<PictureHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureHolder {
        val binging =
            PagerItemPictureBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PictureHolder(binging.root)
    }

    override fun getItemCount(): Int = pictures.size

    override fun onBindViewHolder(holder: PictureHolder, position: Int) {
        holder.bind(pictures[position])
    }
}

class PictureHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

    internal fun bind(media: Uri) {
        itemView.findViewById<ImageView>(R.id.pager_picture)
            .apply {
                Glide.with(this).load(media).into(this)
            }
    }
}