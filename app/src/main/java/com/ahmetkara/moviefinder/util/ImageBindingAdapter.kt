package com.ahmetkara.moviefinder.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.ahmetkara.moviefinder.util.Constant
import com.bumptech.glide.Glide

@BindingAdapter("android:imageUrl")
fun loadImage(imageView: ImageView, url : String?){
    Glide.with(imageView.context)
        .load(Constant.IMAGE_BASE_URL + Constant.IMAGE_W780 + url)
        .into(imageView)
}