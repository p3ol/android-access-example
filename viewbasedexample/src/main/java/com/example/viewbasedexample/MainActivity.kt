package com.example.viewbasedexample

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.viewbasedexample.model.Article
import com.example.viewbasedexample.ui.home.HomeViewModel
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {

    private lateinit var homeViewModel: HomeViewModel

    private lateinit var bottomBarNav: NavigationBarView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomBarNav = findViewById(R.id.bottom_navigation)

        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        bottomBarNav.setupWithNavController(navController)
    }
}
