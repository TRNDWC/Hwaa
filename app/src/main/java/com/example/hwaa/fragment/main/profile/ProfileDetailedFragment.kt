package com.example.hwaa.fragment.main.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.hwaa.R
import com.example.hwaa.activity.main.MainActivity
import com.example.hwaa.core.base.BaseFragment
import com.example.hwaa.databinding.FragmentProfileDetailedBinding
import com.example.hwaa.navigation.profile.ProfileNavigation
import com.example.hwaa.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileDetailedFragment :
    BaseFragment<FragmentProfileDetailedBinding, ProfileViewModel>(R.layout.fragment_profile_detailed) {
    val viewModel: ProfileViewModel by viewModels()
    override fun getVM() = viewModel

    @Inject
    lateinit var profileNavigation: ProfileNavigation

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val topProfile = binding.topProfile
        topProfile.ivSetting.setOnClickListener {
            profileNavigation.navigateToSetting()
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).setToolbarProfile()
    }
}