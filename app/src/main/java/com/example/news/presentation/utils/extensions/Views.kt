package com.example.news.presentation.utils.extensions

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.news.R
import com.google.android.material.snackbar.Snackbar


//region View Visibility
/**
 * Set view visibility visible
 */
fun View.visible() {
    visibility = View.VISIBLE
}

/**
 * Set view visibility invisible
 */
fun View.invisible() {
    visibility = View.INVISIBLE
}

/**
 * Set view visibility gone
 */
fun View.gone() {
    visibility = View.GONE
}

//endregion
fun RecyclerView.linearLayoutManager(@RecyclerView.Orientation orientation: Int = RecyclerView.VERTICAL) {
    layoutManager = LinearLayoutManager(context, orientation, false)
}

fun View.showLongSnackBar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG)
        .setBackgroundTint(ContextCompat.getColor(context, R.color.black)).show()
}