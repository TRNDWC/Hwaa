package com.example.hwaa.fragment.start

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.hwaa.R
import com.example.hwaa.activity.main.MainActivity
import com.example.hwaa.activity.start.StartActivity
import com.example.hwaa.core.base.BaseFragment
import com.example.hwaa.core.base.BaseFragmentNotRequireViewModel
import com.example.hwaa.databinding.FragmentStartBinding
import com.example.hwaa.navigation.start.StartNavigation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class StartFragment : BaseFragment<FragmentStartBinding, StartViewModel>(R.layout.fragment_start) {

    @Inject
    lateinit var startNavigation: StartNavigation
    private val viewModel: StartViewModel by viewModels()
    override fun getVM(): StartViewModel {
        return viewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_start, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.startButton.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                (activity as StartActivity).moveToMainActivity()
            }
        }
    }
}