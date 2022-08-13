package com.example.news.presentation.newsDetails

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.news.databinding.FragmentNewsDetailsBinding
import com.example.news.domain.entity.TopHeadlinesEntity
import com.example.news.R


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
        setToolbarMenuAction()

    }

    private fun setToolbarMenuAction()
    {

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.itemFav -> {
                        findNavController().navigate(R.id.actionNewsDetailsToWishlist)
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

    }

}