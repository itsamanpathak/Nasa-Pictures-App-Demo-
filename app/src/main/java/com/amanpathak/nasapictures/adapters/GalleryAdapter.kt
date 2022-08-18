package com.amanpathak.nasapictures.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.amanpathak.nasapictures.R
import com.amanpathak.nasapictures.databinding.GalleryItemBinding
import com.amanpathak.nasapictures.helper.Validator
import com.amanpathak.nasapictures.models.PhotoModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions


class GalleryAdapter(private val context : Context, private val selectionInterface: GalleryAdapter.ImageSelectionInterface, private val imageList: List<PhotoModel>, private val type : TYPE) :
    RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {
    enum class TYPE(val value : Int) {
        GALLERY(1), PHOTO_DETAIL(2)
    }

    inner class ViewHolder(var item: ViewBinding) : RecyclerView.ViewHolder(item.root) {

        fun bind(data : PhotoModel, position: Int){


            if(item is GalleryItemBinding){
                if(Validator.isValidUrl(data.thumbnail)){

                    Glide.with(context).load(data.thumbnail)
                        .sizeMultiplier(0.6f)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .placeholder(R.drawable.ic_placeholder).
                        into((item as GalleryItemBinding).image)
                    (item as GalleryItemBinding).image.transitionName = "photo$position"

                    itemView.setOnClickListener {
                        selectionInterface.onImageSelect(data, position, (item as GalleryItemBinding).image)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = GalleryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return    ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = imageList[position]
        holder.bind(data, holder.adapterPosition)
    }

    override fun getItemViewType(position: Int): Int {
        return if(type == TYPE.GALLERY){
            return TYPE.GALLERY.value
        }
        else
            return TYPE.PHOTO_DETAIL.value
    }

    override fun getItemCount(): Int {
        return imageList.size
    }


    interface ImageSelectionInterface{
        fun onImageSelect(photoItem: PhotoModel, position : Int, view: ImageView)
    }

}