package com.konifar.materialcat.views.adapters

import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.GridView
import com.konifar.materialcat.R
import com.konifar.materialcat.databinding.ItemPhotoBinding
import com.konifar.materialcat.models.pojo.Photo
import com.squareup.picasso.Picasso
import java.util.*

class PhotosArrayAdapter(context: Context) : ArrayAdapter<Photo>(context, R.layout.item_photo, ArrayList<Photo>()) {

    private var lastPosition = -1

    override fun getView(pos: Int, view: View?, parent: ViewGroup): View {
        var view = view
        val holder: ViewHolder

        if (view == null || view.tag == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_photo, parent, false)
            holder = ViewHolder(view)
        } else {
            holder = view.tag as ViewHolder
        }

        view!!.tag = holder

        bindData(holder, getItem(pos))
        initListeners(holder, pos, view, parent)

        if (pos > 1) startAnimation(view, pos)

        return view
    }

    private fun initListeners(holder: ViewHolder, pos: Int, view: View, parent: ViewGroup) {
        holder.binding.ripple.setOnClickListener { (parent as GridView).performItemClick(view, pos, 0L) }
    }

    private fun bindData(holder: ViewHolder, photo: Photo) {
        val imageUrl = photo.imageUrl

        if (holder.binding.imgPreview.tag == null || holder.binding.imgPreview.tag != imageUrl) {
            Picasso.with(context)
                    .load(imageUrl)
                    .placeholder(R.color.theme50)
                    .into(holder.binding.imgPreview)
            holder.binding.imgPreview.tag = imageUrl
        }
    }

    fun addAll(photos: List<Photo>?) {
        if (photos == null) return

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            super.addAll(photos)
        } else {
            for (item in photos) {
                super.add(item)
            }
        }
    }

    internal fun startAnimation(view: View, pos: Int) {
        if (lastPosition < pos) {
            val anim = AnimationUtils.loadAnimation(context, R.anim.item_scale_in)
            view.startAnimation(anim)
            lastPosition = pos
        }
    }

    internal class ViewHolder(view: View) {

        var binding: ItemPhotoBinding

        init {
            binding = DataBindingUtil.bind(view)
        }
    }

}
