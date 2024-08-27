package com.example.hwaa

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.hwaa.core.base.BaseActivity
import com.example.hwaa.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding, MainActivityViewModel>(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding.textView.text = "Hello World"
    }

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun getVM(): MainActivityViewModel {
        return MainActivityViewModel()
    }
}