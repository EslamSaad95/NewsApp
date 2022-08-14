package com.example.news.presentation.wishlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.news.databinding.FragmentWishlistBinding
import com.example.news.presentation.utils.extensions.gone
import com.example.news.presentation.utils.extensions.linearLayoutManager
import com.example.news.presentation.utils.extensions.showLongSnackBar
import com.example.news.presentation.utils.extensions.visible
import com.example.news.R

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
        observeWishlistLiveData()
        observeFavItemLiveData()
        observeSnackBarLiveData()
        initWishlistRv()
    }

    private fun observeSnackBarLiveData()
    {
        viewModel.snackBarLiveData.observe(viewLifecycleOwner){
            it?.let { binding.rvWishlist.showLongSnackBar(it) }
        }
    }
    private fun observeWishlistLiveData()
    {
        viewModel.favNewsLiveData.observe(viewLifecycleOwner){
            it?.let {
                if(it.isEmpty())
                binding.tvEmpty.visible()
                else
                {
                    binding.tvEmpty.gone()
                    wishlistRvAdapter.fill(it)
                }
            }
        }
    }

    private fun observeFavItemLiveData() {
        viewModel.favItemUpdateLiveData.observe(viewLifecycleOwner) {
            it?.let { favCheck ->
                wishlistRvAdapter.getItem(position = favCheck.position).isFav = favCheck.isFav
                wishlistRvAdapter.notifyItemChanged(favCheck.position)
            }
        }
    }


    private fun initWishlistRv()
    {
        binding.rvWishlist.apply {
            linearLayoutManager()
            adapter=wishlistRvAdapter
        }
        wishlistRvAdapter.setOnClickListener { clickedView, item, position ->
            if (clickedView.id == R.id.ivFav) {
                if (item.isFav.not())
                    viewModel.addToDatabase(item, position)
                else
                    viewModel.removeFromDatabase(item, position)
            } else
                findNavController().navigate(
                    R.id.actionHomeToNewsDetails,
                    bundleOf("detailsObj" to item)
                )
        }
    }


}