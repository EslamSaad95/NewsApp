package com.example.news.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.news.R
import com.example.news.databinding.FragmentHomeBinding
import com.example.news.domain.common.ApiFailure
import com.example.news.presentation.home.adapter.LatestNewsRvAdapter
import com.example.news.presentation.home.adapter.SliderVpAdapter
import com.example.news.presentation.utils.common.HorizontalMarginItemDecoration
import com.example.news.presentation.utils.extensions.linearLayoutManager
import com.example.news.presentation.utils.extensions.showLongSnackBar
import com.example.news.presentation.utils.extensions.visible
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    private val sliderAdapter by lazy { SliderVpAdapter() }
    private val viewModel by viewModels<HomeViewModel>()
    private val latestNewsAdapter by lazy { LatestNewsRvAdapter() }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@HomeFragment.viewModel
        }
        observeEgyptNewsSliderLiveData()
        observeLatestNewsLiveData()
        observeErrorLiveData()
        observeSnackBarLiveData()
        observeFavItemLiveData()
    }

    private fun observeSnackBarLiveData() {
        viewModel.snackBarLiveData.observe(viewLifecycleOwner) {
            it?.let {
                binding.rvLatestNews.showLongSnackBar(it)
                viewModel.snackBarLiveData.value = null
            }
        }
    }

    private fun observeFavItemLiveData() {
        viewModel.favItemUpdateLiveData.observe(viewLifecycleOwner) {
            it?.let { favCheck ->
                val isSliderContainsItem =
                    sliderAdapter.getCurrentItems().any { i -> i.title == it.title }
                if (isSliderContainsItem) {
                    sliderAdapter.getItem(position = favCheck.position).isFav = favCheck.isFav
                } else {
                    latestNewsAdapter.getItem(position = favCheck.position).isFav = favCheck.isFav
                    latestNewsAdapter.notifyItemChanged(favCheck.position)
                }
            }
        }
    }

    private fun observeEgyptNewsSliderLiveData() {
        viewModel.egyptNewsLiveData.observe(viewLifecycleOwner) {
            sliderAdapter.fill(it)
            initSliderVp()
        }
    }

    private fun observeLatestNewsLiveData() {
        viewModel.latestNewsEntity.observe(viewLifecycleOwner) {
            it?.let {
                latestNewsAdapter.fill(it)
                initLatestNewsRv()
            }
        }
    }

    private fun observeErrorLiveData() {
        viewModel.errorLiveData.observe(viewLifecycleOwner) {

            if (it is ApiFailure.ConnectionError)
                binding.tvErrorMsg.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    R.drawable.ic_noconnection,
                    0,
                    0
                )
            else
                binding.tvErrorMsg.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            binding.tvErrorMsg.apply {
                visible()
                it.error?.let { text = it }
                it.errorResId?.let { text = getText(it) }
            }
        }
    }

    private fun initSliderVp() {

        val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
        val currentItemHorizontalMarginPx =
            resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = -pageTranslationX * position
            page.scaleY = 1 - (0.25f * abs(position))
        }

        val itemDecoration = HorizontalMarginItemDecoration(
            requireContext(),
            R.dimen.viewpager_current_item_horizontal_margin
        )



        if (binding.vpSlider.itemDecorationCount == 0) {
            binding.vpSlider.apply {
                adapter = sliderAdapter
                orientation = ViewPager2.ORIENTATION_HORIZONTAL
                offscreenPageLimit = 1
                setPageTransformer(pageTransformer)
                addItemDecoration(itemDecoration)
            }
        }


        TabLayoutMediator(binding.tbSlider, binding.vpSlider) { _, _ -> }.attach()
        sliderAdapter.setOnClickListener { clickedView, item, position ->

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


    private fun initLatestNewsRv() {
        binding.rvLatestNews.apply {
            linearLayoutManager()
            adapter = latestNewsAdapter
        }
        latestNewsAdapter.setOnClickListener { clickedView, item, position ->
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