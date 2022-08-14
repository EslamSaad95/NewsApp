package com.example.news.presentation.wishlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.example.news.domain.entity.TopHeadlinesEntity
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel

@AndroidEntryPoint
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
        viewModel.getFavNews()
        observeWishlistLiveData()
        observeFavItemLiveData()
        observeSnackBarLiveData()
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
                checkEmptyFavNews(it.size)
                wishlistRvAdapter.fill(it)
                initWishlistRv()
            }
        }
    }

    private fun observeFavItemLiveData() {
        viewModel.favItemUpdateLiveData.observe(viewLifecycleOwner) {
            it?.let { favCheck ->
                wishlistRvAdapter.removeItem(it.position)
                checkEmptyFavNews(wishlistRvAdapter.itemCount)
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
                    R.id.actionWishlistToNewsDetails,
                    bundleOf("detailsObj" to item)
                )
        }
    }
private fun checkEmptyFavNews(count:Int)
{
    if(count==0)
        binding.tvEmpty.visible()
    else
        binding.tvEmpty.gone()

}

}