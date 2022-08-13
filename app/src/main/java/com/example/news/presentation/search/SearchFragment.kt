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
import com.example.news.presentation.utils.extensions.gone
import com.example.news.presentation.utils.extensions.hideKeyboard
import com.example.news.presentation.utils.extensions.linearLayoutManager
import com.example.news.presentation.utils.extensions.visible
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchFragment : Fragment() {
    private val binding by lazy { FragmentSearchBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<SearchViewModel>()
    private val searchAdapter by lazy { SearchResultsRvAdapter() }

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

    override fun onResume() {
        super.onResume()
        requireContext().hideKeyboard(requireView())
    }

    private fun observeResultsLiveData() {
        viewModel.searchResultsLiveData.observe(viewLifecycleOwner) {
            it?.let {
                if (it.isEmpty())
                        binding.tvEmpty.visible()
                else {
                    binding.tvEmpty.gone()
                    searchAdapter.fill(it)
                }
            }
        }
    }

    private fun observeErrorLiveData() {
        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            if (it is ApiFailure.ConnectionError)
                binding.tvError.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    R.drawable.ic_noconnection,
                    0,
                    0
                )
            else
                binding.tvError.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)

            binding.tvError.apply {
                visible()
                it.error?.let { text = it }
                it.errorResId?.let { text = getText(it) }
            }
        }
    }

    private fun setViewsListeners() {

        binding.svNews.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isEmpty())
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.search_fr_validation_empty_keyword),
                        Toast.LENGTH_LONG
                    ).show()
                else {
                    requireActivity().hideKeyboard(requireView())
                    viewModel.getSearchResults(query)

                }
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                // your code
                return false
            }
        })

    }

    private fun setViewsClickListeners() {
        val searchCloseButtonId: Int = binding.svNews.context.resources
            .getIdentifier("android:id/search_close_btn", null, null)
        val closeButton: ImageView = binding.svNews.findViewById(searchCloseButtonId)
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