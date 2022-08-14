package com.example.news.presentation.wishlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.news.databinding.FragmentWishlistBinding


class WishlistFragment : Fragment() {
    private val binding by lazy { FragmentWishlistBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<WishlistViewModel>()
    private val wishlistRvAdapter by lazy { WishlistRvAdapter() }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


}