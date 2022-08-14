package com.example.news.presentation.search

import com.example.news.databinding.ItemNewsBinding
import com.example.news.domain.entity.TopHeadlinesEntity
import com.example.news.presentation.utils.base.BaseAdapter

class SearchResultsRvAdapter : BaseAdapter<ItemNewsBinding, TopHeadlinesEntity>() {
    override fun setContent(binding: ItemNewsBinding, item: TopHeadlinesEntity, position: Int) {
        binding.apply {
            news = item
            root.setOnClickListener { onViewClicked(it, item, position) }
            ivFav.setOnClickListener {
                onViewClicked(it, item, position)
            }
        }
    }
}