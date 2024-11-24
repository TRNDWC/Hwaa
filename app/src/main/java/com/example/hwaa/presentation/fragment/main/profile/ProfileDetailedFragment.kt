package com.example.hwaa.presentation.fragment.main.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.hwaa.R
import com.example.hwaa.data.model.UserModel
import com.example.hwaa.presentation.activity.main.MainActivity
import com.example.hwaa.presentation.core.base.BaseFragment
import com.example.hwaa.databinding.FragmentProfileDetailedBinding
import com.example.hwaa.domain.entity.UserEntity
import com.example.hwaa.presentation.navigation.profile.ProfileNavigation
import com.example.hwaa.presentation.util.UserProvider
import com.example.hwaa.presentation.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileDetailedFragment :
    BaseFragment<FragmentProfileDetailedBinding, ProfileViewModel>(R.layout.fragment_profile_detailed) {
    val viewModel: ProfileViewModel by viewModels()
    override fun getVM() = viewModel
    private lateinit var user: UserModel

    @Inject
    lateinit var profileNavigation: ProfileNavigation

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val topProfile = binding.topProfile
        topProfile.ivSetting.setOnClickListener {
            profileNavigation.navigateToSetting()
        }
        UserProvider.getUser().let {
            user = it
        }
    }

    private fun loadUser() {
        UserProvider.getUser().let {
            user = it
        }
        binding.apply {
            Glide.with(requireContext())
                .load(user.profileImage)
                .centerCrop()
                .placeholder(R.drawable.ic_profile)
                .into(topProfile.ivAvatar)

            topProfile.tvName.text = user.displayName
            topProfile.tvEmail.text = user.email
            topProfile.tvLevel.text = user.level.name.lowercase()
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).setToolbarProfile()
        loadUser()
    }
}