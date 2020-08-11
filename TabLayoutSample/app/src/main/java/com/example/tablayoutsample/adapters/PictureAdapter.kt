package com.example.tablayoutsample.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tablayoutsample.databinding.PagerItemPictureBinding

/**
 * ページャーに画像を出力する
 */
class PictureAdapter(private val pictures: List<Uri>) : RecyclerView.Adapter<PictureHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureHolder {

        return PictureHolder(
            PagerItemPictureBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = pictures.size

    override fun onBindViewHolder(holder: PictureHolder, position: Int) {
        holder.bind(pictures[position])
    }
}

class PictureHolder(private val binding: PagerItemPictureBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(media: Uri) {
        binding.pagerPicture
            .apply {
                Glide.with(this).load(media).into(this)
            }
    }
}