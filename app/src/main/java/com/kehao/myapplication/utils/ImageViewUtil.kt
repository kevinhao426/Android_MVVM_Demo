package com.kehao.myapplication.utils

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

fun getProgressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context)
        .apply {
            strokeWidth = 10f
            centerRadius = 50f
            start()
        }
}

fun ImageView.loadImage(uri: String?, progressDrawable: CircularProgressDrawable) {
    val options = RequestOptions()
        .placeholder(progressDrawable)
    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(uri)
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .into(this)
}

@BindingAdapter("android:imageUrl")
fun loadImage(view: ImageView, uri: String?) {
    view.loadImage(
        uri,
        getProgressDrawable(view.context)
    )
}