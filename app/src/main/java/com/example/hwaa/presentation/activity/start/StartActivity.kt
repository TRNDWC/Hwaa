package com.example.hwaa.presentation.activity.start

import android.content.Intent
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.example.hwaa.R
import com.example.hwaa.presentation.activity.main.MainActivity
import com.example.hwaa.presentation.core.base.BaseActivityNotRequireViewModel
import com.example.hwaa.databinding.ActivityStartBinding
import com.example.hwaa.presentation.navigation.start.StartNavigation
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class StartActivity : BaseActivityNotRequireViewModel<ActivityStartBinding>() {

    override val layoutId: Int = R.layout.activity_start
    @Inject
    lateinit var startNavigation: StartNavigation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        startNavigation.bind(navController)
    }

    fun moveToMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.start_main_enter, R.anim.start_main_exit)
        finish()
    }
}