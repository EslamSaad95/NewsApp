package com.example.news.presentation.utils.extensions

import android.text.Html
import android.text.method.ScrollingMovementMethod
import android.widget.ImageView
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import com.google.android.material.textview.MaterialTextView

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {
    url?.let { view.load(it) }
}

@BindingAdapter("imageUrlR15")
fun loadImageR15(view: ImageView, url: String?) {
    url?.let { view.load(it, 15) }
}


@BindingAdapter("scrollText")
fun scrollText(textView: MaterialTextView, content: String?) {
    content?.let {
        textView.apply {
            textView.text=content.toString()
            movementMethod = ScrollingMovementMethod()
        }
    }

}