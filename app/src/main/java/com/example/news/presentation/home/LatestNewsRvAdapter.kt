package com.example.news.presentation.home

import com.example.news.databinding.ItemNewsBinding
import com.example.news.domain.entity.TopHeadlinesEntity
import com.example.news.presentation.utils.base.BaseAdapter

class LatestNewsRvAdapter : BaseAdapter<ItemNewsBinding, TopHeadlinesEntity>() {
    override fun setContent(binding: ItemNewsBinding, item: TopHeadlinesEntity, position: Int) {
        binding.apply {
            root.setOnClickListener { onViewClicked(it, item, position) }
            news = item
        }
    }
}