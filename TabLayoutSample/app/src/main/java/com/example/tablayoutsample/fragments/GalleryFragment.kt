package com.example.tablayoutsample.fragments

import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.tablayoutsample.adapters.PictureAdapter
import com.example.tablayoutsample.databinding.FragmentGalleryBinding
import com.example.tablayoutsample.databinding.ItemTabPictureBinding
import com.google.android.material.tabs.TabLayoutMediator

/**
 * ページャーとタブにストレージのJPEG画像を出力するフラグメント.
 */
class GalleryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentGalleryBinding.inflate(inflater, container, false)
            .apply {
                // MediaストアからJPEG画像のリストを取得
                val pictures = getPictures()

                if (pictures.isNotEmpty()) {
                    /*
                        ページャーの初期化
                     */
                    val adapter = PictureAdapter(pictures)
                    viewPager.adapter = adapter

                    /*
                        タブの初期化
                     */
                    TabLayoutMediator(tabs, viewPager) { tab, position ->
                        ItemTabPictureBinding.inflate(inflater, container, false)
                            .apply {
                                tabPicture.apply {
                                    tab.view.addView(this)
                                    // タブにサムネイルを設定する.
                                    Glide.with(this)
                                        .load(pictures[position])
                                        .thumbnail(THUMBNAIL_SIZE_MULTIPLIER)
                                        .into(this)
                                }
                            }
                    }.attach()
                } else {
                    Toast.makeText(requireContext(), "JPEG画像が存在しません.", Toast.LENGTH_SHORT).show()
                }


            }

        return binding.root
    }

    /**
     * Mediaストア からjpeg画像のリストを取得する.
     */
    private fun getPictures(): List<Uri> {
        val projection = arrayOf(MediaStore.MediaColumns._ID)
        val selection = "${MediaStore.MediaColumns.DISPLAY_NAME} like ?"
        val selectionArgs = arrayOf("%.jpg")
        val cursor = requireContext().contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            null
        )

        val pictures = mutableListOf<Uri>()
        cursor?.use {
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns._ID)
            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val contentUri = Uri.withAppendedPath(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    id.toString()
                )
                pictures.add(contentUri)
            }
        }
        return pictures
    }

    companion object {
        const val THUMBNAIL_SIZE_MULTIPLIER = 0.25f
    }
}