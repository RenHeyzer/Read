package com.example.read.main.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.read.R
import com.example.read.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var auth: FirebaseAuth

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        setupNavigation()
    }

    private fun setupNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        NavigationUI.setupWithNavController(binding.bottomNavigation, navController)

        onDestinationChanged(navController)
    }

    private fun onDestinationChanged(navController: NavController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.signUpFragment -> visibilityOfBars(
                    topBarVisibility = false,
                    bottomBarVisibility = false
                )

                R.id.singInFragment -> visibilityOfBars(
                    topBarVisibility = false,
                    bottomBarVisibility = false
                )

                R.id.signUpSuccessDialogFragment -> visibilityOfBars(
                    topBarVisibility = false,
                    bottomBarVisibility = false
                )

                else -> visibilityOfBars(
                    topBarVisibility = true,
                    bottomBarVisibility = true
                )
            }
        }
    }

    private fun visibilityOfBars(topBarVisibility: Boolean, bottomBarVisibility: Boolean) =
        with(binding) {
            appbar.isVisible = topBarVisibility
            bottomNavigation.isVisible = bottomBarVisibility
        }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        currentUser?.reload()
    }
}