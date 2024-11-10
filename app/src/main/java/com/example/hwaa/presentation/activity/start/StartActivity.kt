package com.example.hwaa.presentation.activity.start

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.example.hwaa.R
import com.example.hwaa.presentation.activity.main.MainActivity
import com.example.hwaa.presentation.core.base.BaseActivityNotRequireViewModel
import com.example.hwaa.databinding.ActivityStartBinding
import com.example.hwaa.domain.usecase.auth.IsLoggedInUseCase
import com.example.hwaa.presentation.core.base.BaseActivity
import com.example.hwaa.presentation.fragment.start.StartViewModel
import com.example.hwaa.presentation.navigation.start.StartNavigation
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class StartActivity : BaseActivity<ActivityStartBinding, StartViewModel>() {

    override val layoutId: Int = R.layout.activity_start
    @Inject
    lateinit var startNavigation: StartNavigation

    private val startViewModel: StartViewModel by viewModels()
    override fun getVM() = startViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        startNavigation.bind(navController)
    }

    override fun onStart() {
        super.onStart()
        startViewModel.checkLogin()

        if (startViewModel.isLoggedIn) {
            moveToMainActivity()
        }
    }

    fun moveToMainActivity(){
        Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.start_main_enter, R.anim.start_main_exit)
        finish()
    }
}