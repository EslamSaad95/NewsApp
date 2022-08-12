package com.example.news.presentation.extensions

import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {
    url?.let { view.load(it) }
}

@BindingAdapter("imageUrlR15")
fun loadImageR15(view: ImageView, url: String?) {
    url?.let { view.load(it, 15) }
}