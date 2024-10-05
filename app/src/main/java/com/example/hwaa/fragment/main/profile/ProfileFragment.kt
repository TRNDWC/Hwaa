package com.example.hwaa.fragment.main.profile

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.example.hwaa.R
import com.example.hwaa.activity.main.MainActivity
import com.example.hwaa.core.base.BaseFragment
import com.example.hwaa.databinding.FragmentProfileBinding
import com.example.hwaa.fragment.main.forum.ForumFragment
import com.example.hwaa.navigation.profile.ProfileNavigation
import com.example.hwaa.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment :
    BaseFragment<FragmentProfileBinding, ProfileViewModel>(R.layout.fragment_profile) {

    @Inject
    lateinit var profileNavigation: ProfileNavigation

    private val viewModel: ProfileViewModel by viewModels()
    override fun getVM() = viewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navHost =
            childFragmentManager.findFragmentById(R.id.profileNavHostFragment) as NavHostFragment
        val navController = navHost.navController
        profileNavigation.bind(navController)
    }

    companion object {
        fun newInstance(key: String, color: String): Fragment {
            val fragment = ProfileFragment()
            val argument = Bundle()
            fragment.arguments = argument
            return fragment
        }
    }
}