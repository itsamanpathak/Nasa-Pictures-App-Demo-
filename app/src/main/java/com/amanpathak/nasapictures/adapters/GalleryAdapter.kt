package com.amanpathak.nasapictures.adapters

import android.R
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.Nullable
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.amanpathak.nasapictures.databinding.GalleryItemBinding
import com.amanpathak.nasapictures.databinding.ImagedetailItemBinding
import com.amanpathak.nasapictures.helper.Validator
import com.amanpathak.nasapictures.models.PhotoModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target


class GalleryAdapter(
    private val context: Context,
    private val selectionInterface: GalleryAdapter.Interaction,
    private val imageList: List<PhotoModel>,
    private val type: TYPE
) :
    RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {
    enum class TYPE(val value: Int) {
        GALLERY(1), PHOTO_DETAIL(2)
    }

    inner class ViewHolder(var item: ViewBinding) : RecyclerView.ViewHolder(item.root) {

        @SuppressLint("ClickableViewAccessibility")
        fun bind(data: PhotoModel, position: Int) {


            if (item is GalleryItemBinding) {
                if (Validator.isValidUrl(data.thumbnail)) {

                    Glide.with(context).load(data.thumbnail)
                        .sizeMultiplier(0.3f)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .placeholder(com.amanpathak.nasapictures.R.drawable.nasa_placeholder)
                        .into((item as GalleryItemBinding).image)


                    itemView.setOnClickListener {
                        selectionInterface.onImageSelect(
                            data,
                            position,
                            (item as GalleryItemBinding).image
                        )
                    }
                } else {
                    Glide.with(context)
                        .load(com.amanpathak.nasapictures.R.drawable.nasa_placeholder)
                        .into((item as GalleryItemBinding).image)

                }
            } else if (item is ImagedetailItemBinding) {

                (item as ImagedetailItemBinding).progress.isVisible = true

                val thumbnail = Glide.with(context).load(data.thumbnail)
                    .addListener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            (item as ImagedetailItemBinding).progress.isVisible = false
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            (item as ImagedetailItemBinding).progress.isVisible = false
                            return false
                        }

                    })

                Glide.with(context).load(data.url)
                    .addListener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            (item as ImagedetailItemBinding).progress.isVisible = false
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            (item as ImagedetailItemBinding).progress.isVisible = false
                            return false
                        }
                    })
                    .thumbnail(thumbnail)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into((item as ImagedetailItemBinding).image)



                //To Handle Pinch Zoom In/Zoom out
                (item as ImagedetailItemBinding).image.apply {

                    setOnTouchListener { view, event ->
                        var result = true
                        if (event.pointerCount >= 2 || view.canScrollHorizontally(1) && canScrollHorizontally(
                                -1
                            )
                        ) {
                            result = when (event.action) {
                                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                                    parent.requestDisallowInterceptTouchEvent(true)
                                    false
                                }
                                MotionEvent.ACTION_UP -> {
                                    parent.requestDisallowInterceptTouchEvent(false)
                                    true
                                }
                                else -> true
                            }
                        }
                        result
                    }

                }


            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == TYPE.GALLERY.value) {
            val binding =
                GalleryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ViewHolder(binding)
        } else {

            val binding =
                ImagedetailItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = imageList[position]
        holder.bind(data, holder.adapterPosition)
    }

    override fun getItemViewType(position: Int): Int {
        return if (type == TYPE.GALLERY) {
            return TYPE.GALLERY.value
        } else
            return TYPE.PHOTO_DETAIL.value
    }

    override fun getItemCount(): Int {
        return imageList.size
    }


    interface Interaction {
        fun onImageSelect(photoItem: PhotoModel, position: Int, view: ImageView)
    }

}