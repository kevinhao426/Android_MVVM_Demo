package com.kehao.myapplication.view

import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.kehao.myapplication.R
import com.kehao.myapplication.base.BaseActivity
import com.kehao.myapplication.databinding.ActivityMainBinding
import com.kehao.myapplication.viewModel.MainActivityViewModel

class MainActivity : BaseActivity<MainActivityViewModel, ActivityMainBinding>() {
    private lateinit var navController: NavController

    override fun getContentViewId() = R.layout.activity_main

    override fun observeData() {
    }

    override fun handleEvent() {
        navController = Navigation.findNavController(this, R.id.fragment)
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onSupportNavigateUp(): Boolean =
        NavigationUI.navigateUp(navController, null as DrawerLayout?)
    
}