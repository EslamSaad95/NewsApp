package com.example.news.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.news.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    private val sliderAdapter by lazy { SliderVpAdapter() }
    private val viewModel by viewModels<HomeViewModel>()

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
    }


    private fun observeEgyptNewsSliderLiveData()
    {
        viewModel.egyptNewsLiveData.observe(viewLifecycleOwner){
            sliderAdapter.fill(it)
            initSliderVp()
        }
    }

    private fun initSliderVp()
    {
        binding.apply {
            vpSlider.adapter = sliderAdapter
            vpSlider.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            TabLayoutMediator(tbSlider, vpSlider) { _, _ -> }.attach()
        }


        binding.vpSlider.setPageTransformer { page, position ->
            val pageWidth: Int =
                binding.vpSlider.measuredWidth - binding.vpSlider.paddingLeft - binding.vpSlider.paddingRight
            val pageHeight: Int = binding.vpSlider.height
            val paddingLeft: Int = binding.vpSlider.paddingLeft
            val transformPos =
                (page.left - (binding.vpSlider.scrollX + paddingLeft)) as Float / pageWidth

            val normalizedposition = Math.abs(Math.abs(transformPos) - 1)
            page.alpha = normalizedposition + 0.5f

            val max = -pageHeight / 10

            if (transformPos < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                page.translationY = 0F
            } else if (transformPos <= 1) { // [-1,1]
                page.translationY = max * (1 - Math.abs(transformPos))
            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                page.setTranslationY(0)
            }

        }
    }

}