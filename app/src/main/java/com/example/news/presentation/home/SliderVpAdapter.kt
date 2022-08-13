package com.example.news.presentation.home

import com.example.news.databinding.ItemTopHeadlineBinding
import com.example.news.domain.entity.EgyptNewsEntity
import com.example.news.presentation.utils.base.BaseAdapter

class SliderVpAdapter : BaseAdapter<ItemTopHeadlineBinding, EgyptNewsEntity>() {
    override fun setContent(binding: ItemTopHeadlineBinding, item: EgyptNewsEntity, position: Int) {
        binding.apply {
            root.setOnClickListener { onViewClicked(it, item, position) }
            news = item
        }
    }
}