package com.example.news.presentation.extensions

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.linearLayoutManager(@RecyclerView.Orientation orientation: Int = RecyclerView.VERTICAL) {
    layoutManager = LinearLayoutManager(context, orientation, false)
}