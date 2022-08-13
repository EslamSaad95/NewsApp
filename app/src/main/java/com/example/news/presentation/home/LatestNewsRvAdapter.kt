package com.example.news.presentation.home

import com.example.news.databinding.ItemNewsBinding
import com.example.news.domain.entity.LatestNewsEntity
import com.example.news.presentation.base.BaseAdapter

class LatestNewsRvAdapter : BaseAdapter<ItemNewsBinding, LatestNewsEntity>() {
    override fun setContent(binding: ItemNewsBinding, item: LatestNewsEntity, position: Int) {
        binding.apply {
            root.setOnClickListener { onViewClicked(it, item, position) }
            news = item
        }
    }
}