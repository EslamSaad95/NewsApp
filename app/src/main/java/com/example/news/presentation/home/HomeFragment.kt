package com.example.news.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.news.databinding.FragmentHomeBinding
import com.example.news.presentation.extensions.linearLayoutManager
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint


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
        observeEgyptNewsSliderLiveData()
        observeLatestNewsLiveData()
    }


    private fun observeEgyptNewsSliderLiveData() {
        viewModel.egyptNewsLiveData.observe(viewLifecycleOwner) {
            sliderAdapter.fill(it)
            initSliderVp()
        }
    }

    private fun observeLatestNewsLiveData()
    {
        viewModel.latestNewsEntity.observe(viewLifecycleOwner){
            it?.let {
                latestNewsAdapter.fill(it)
                initLatestNewsRv()
            }
        }
    }

    private fun initSliderVp() {
        binding.apply {
            vpSlider.adapter = sliderAdapter
            vpSlider.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            TabLayoutMediator(tbSlider, vpSlider) { _, _ -> }.attach()
        }


        binding.vpSlider.setPageTransformer(MarginPageTransformer(1500));
    }


    private fun initLatestNewsRv() {
        binding.rvLatestNews.apply {
            linearLayoutManager()
            adapter = latestNewsAdapter
        }
    }


}