package com.example.news.presentation.newsDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.news.databinding.FragmentNewsDetailsBinding
import com.example.news.domain.entity.TopHeadlinesEntity


class NewsDetailsFragment : Fragment() {

    private val binding by lazy { FragmentNewsDetailsBinding.inflate(layoutInflater) }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        arguments?.getParcelable<TopHeadlinesEntity>("detailsObj")?.let { detailsObj ->
            binding.news = detailsObj
        }

    }

}