package com.example.trashminder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.trashminder.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(_binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment
        _binding.bottomNavigation.setupWithNavController(navHostFragment.navController)
        _binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            bottomNavigationHandler(navHostFragment, menuItem)
            true
        }
        setNavBarVisibility(navHostFragment.navController)
    }

    private fun bottomNavigationHandler(
        navHostFragment: NavHostFragment,
        menuItem: MenuItem
    ) {
        val previousDestinationId =
            navHostFragment.navController.previousBackStackEntry?.destination?.id
        val currentDestinationId = navHostFragment.navController.currentDestination?.id
        if (previousDestinationId == R.id.homeFragment && currentDestinationId == R.id.homeFragment) {
            navHostFragment.navController.popBackStack()
            navHostFragment.navController.navigate(menuItem.itemId)
        } else {
            navHostFragment.navController.popBackStack(
                destinationId = R.id.homeFragment,
                inclusive = false
            )
            navHostFragment.navController.navigate(menuItem.itemId)
        }
    }

    private fun setNavBarVisibility(navController: NavController) {
        navController.addOnDestinationChangedListener { _, direction, _ ->
            when (direction.id) {
                R.id.homeFragment, R.id.calendarFragment, R.id.settingsFragment -> {
                    _binding.bottomNavigation.visibility = View.VISIBLE
                }
                else -> {
                    _binding.bottomNavigation.visibility = View.GONE
                }
            }
        }
    }
}
