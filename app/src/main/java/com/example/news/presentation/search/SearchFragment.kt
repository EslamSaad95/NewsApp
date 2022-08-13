package com.example.news.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.news.R
import com.example.news.databinding.FragmentSearchBinding
import com.example.news.domain.common.ApiFailure
import com.example.news.presentation.utils.extensions.hideKeyboard
import com.example.news.presentation.utils.extensions.linearLayoutManager
import com.example.news.presentation.utils.extensions.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private val binding by lazy { FragmentSearchBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<SearchViewModel>()
    private val searchAdapter by lazy { SearchResultsRvAdapter() }
    private lateinit var closeButton: ImageView

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
            viewModel = this@SearchFragment.viewModel
        }
        initResultsRv()
        observeResultsLiveData()
        observeErrorLiveData()
        setViewsListeners()
        setViewsClickListeners()
    }


    private fun observeResultsLiveData() {
        viewModel.searchResultsLiveData.observe(viewLifecycleOwner) {
            it?.let {
                if (it.isNotEmpty())
                    searchAdapter.fill(it)
                else {
                    binding.tvErrorEmpty.apply {
                        setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            R.drawable.ic_empty_search_results,
                            0,
                            0
                        )
                        text = getText(R.string.search_fr_empty_search_results)
                    }
                }
            }
        }
    }

    private fun observeErrorLiveData() {
        viewModel.errorLiveData.observe(viewLifecycleOwner) {

            if (it is ApiFailure.ConnectionError)
                binding.tvErrorEmpty.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    R.drawable.ic_noconnection,
                    0,
                    0
                )
            binding.tvErrorEmpty.apply {
                visible()
                it.error?.let { text = it }
                it.errorResId?.let { text = getText(it) }
            }
        }
    }

    private fun setViewsListeners() {
        binding.svNews.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                if (p0.isNullOrEmpty())
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.search_fr_validation_empty_keyword),
                        Toast.LENGTH_LONG
                    ).show()
                else {
                    requireActivity().hideKeyboard(requireView())
                    viewModel.getSearchResults(p0)

                }
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })
    }

    private fun setViewsClickListeners() {
        val closeId: Int = binding.svNews.context.resources
            .getIdentifier("android:id/search_close_btn", null, null)
         closeButton = binding.svNews.findViewById(closeId)
        closeButton.setOnClickListener {
            viewModel.getSearchResults("")
            requireContext().hideKeyboard(requireView())
        }
    }


    private fun initResultsRv() {
        binding.rvResults.apply {
            linearLayoutManager()
            adapter = searchAdapter
        }
        searchAdapter.setOnClickListener { clickedView, item, position ->
            findNavController().navigate(
                R.id.actionSearchToNewsDetails,
                bundleOf("detailsObj" to item)
            )
        }
    }

}