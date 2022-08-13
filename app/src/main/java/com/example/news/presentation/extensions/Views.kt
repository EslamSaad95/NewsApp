package com.example.news.presentation.extensions

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


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