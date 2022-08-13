package com.example.news.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.news.R
import com.example.news.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val navController by lazy { (supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment).navController }
    private val appBarConfiguration by lazy {
        AppBarConfiguration(
            setOf(
                R.id.home,
                R.id.search,
                R.id.wishlist,
            )
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initNavigation()
        setOnDestinationChangedListener()
        setToolbar()
    }

    private fun initNavigation() {
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)
    }

    private fun setOnDestinationChangedListener() {
        navController.addOnDestinationChangedListener { _, destination, arguments ->
            setBackBtn(destination.id)
            arguments?.let {
                if (it.getBoolean("showToolbar")) showToolbar() else hideToolbar()
            }
        }
    }

    private fun setToolbar()
    {

        addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_toolbar, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return false
            }
        })


    }
    private fun setBackBtn(id:Int)
    {
        val isTopLevelDestination = appBarConfiguration.topLevelDestinations.contains(id)
        if (isTopLevelDestination.not()) {
            binding.toolbar.apply {
                setNavigationIcon(R.drawable.ic_back)
                setNavigationOnClickListener {
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        }
    }

    private fun hideToolbar() = supportActionBar?.hide()

    private fun showToolbar() = supportActionBar?.show()
}