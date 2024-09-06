package com.example.hwaa.activity.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.hwaa.R
import com.example.hwaa.core.base.BaseActivity
import com.example.hwaa.databinding.ActivityMainBinding
import com.example.hwaa.navigation.AppNavigation
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    @Inject
    lateinit var appNavigation: AppNavigation
    private val viewModel: MainViewModel by viewModels()
    override fun getVM() = viewModel
    override val layoutId = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.bottomNavigationView.background = null
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        appNavigation.bind(navController)
        binding.bottomNavigationView.setupWithNavController(navController)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.start_main_enter, R.anim.start_main_exit)
    }
}