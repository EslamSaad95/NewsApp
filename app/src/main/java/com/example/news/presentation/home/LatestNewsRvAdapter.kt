package com.example.news.presentation.home

import androidx.core.content.ContextCompat
import com.example.news.R
import com.example.news.databinding.ItemNewsBinding
import com.example.news.domain.entity.TopHeadlinesEntity
import com.example.news.presentation.utils.base.BaseAdapter

class LatestNewsRvAdapter : BaseAdapter<ItemNewsBinding, TopHeadlinesEntity>() {
    override fun setContent(binding: ItemNewsBinding, item: TopHeadlinesEntity, position: Int) {
        binding.apply {
            root.setOnClickListener { onViewClicked(it, item, position) }
            ivFav.setOnClickListener { onViewClicked(it, item, position) }
            news = item
            if (item.isFav)
                ivFav.setImageDrawable(
                    ContextCompat.getDrawable(
                        root.context,
                        R.drawable.ic_wishlist_orange
                    )
                )
            else
                ivFav.setImageDrawable(
                    ContextCompat.getDrawable(
                        root.context,
                        R.drawable.ic_nav_wishlist_gray
                    )
                )
        }
    }
}