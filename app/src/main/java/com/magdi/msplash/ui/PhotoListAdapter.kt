package com.magdi.msplash.ui

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.bumptech.glide.request.target.Target
import com.magdi.msplash.R
import com.magdi.msplash.data.Photo
import com.magdi.msplash.images.GlideApp
import com.magdi.msplash.utils.dp
import com.magdi.msplash.utils.px

class PhotoListAdapter : RecyclerView.Adapter<PhotoListAdapter.PhotoViewHolder>() {

    private var photoList: MutableList<Photo> = mutableListOf()

    fun updateList(list: List<Photo>) {
        this.photoList.addAll(list)
        notifyDataSetChanged()
    }

    fun setList(data: List<Photo>) {
        this.photoList.clear()
        this.photoList.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_item, parent, false)
        return PhotoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind2(photoList[position])
    }
    override fun getItemCount(): Int = photoList.size

    class PhotoViewHolder(parent: View) : RecyclerView.ViewHolder(parent) {

        private val imageview: ImageView = parent.findViewById(R.id.image_item)

        fun bind(photo: Photo) {
            imageview.layoutParams.height = photo.height
            imageview.setBackgroundColor(Color.parseColor(photo.color))
            imageview.load(photo.url) {
                crossfade(2000)
                scale(Scale.FILL)
            }
        }

        fun bind2(photo: Photo) {
            imageview.layoutParams.height = photo.height
            imageview.setBackgroundColor(Color.parseColor(photo.color))
            GlideApp.with(imageview.context)
                .load(photo.url)
                .into(imageview)
        }
    }

    companion object {
        private const val TAG = "PhotoListAdapter"
    }
}