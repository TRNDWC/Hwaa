package com.example.hwaa.presentation.fragment.main.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hwaa.R
import com.example.hwaa.presentation.activity.main.MainActivity
import com.example.hwaa.presentation.core.base.BaseFragment
import com.example.hwaa.databinding.FragmentSettingBinding
import com.example.hwaa.presentation.activity.start.StartActivity
import com.example.hwaa.presentation.navigation.profile.ProfileNavigation
import com.example.hwaa.presentation.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingFragment :
    BaseFragment<FragmentSettingBinding, ProfileViewModel>(R.layout.fragment_setting) {

    private val viewModel: ProfileViewModel by viewModels()
    override fun getVM() = viewModel
    private lateinit var settingsAdapter: SettingsAdapter

    @Inject
    lateinit var profileNavigation: ProfileNavigation

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settingsAdapter = SettingsAdapter(
            listOf(
                SettingItem.Header("Account"),
                SettingItem.Item("Username", "Hwaa"),
                SettingItem.Item("Avatar", "Change"),
                SettingItem.Item("About me", "About"),
                SettingItem.Item("Email", "Change"),
                SettingItem.Header("General"),
                SettingItem.Toggle("Notification", true) {},
                SettingItem.Item("Language", "English"),
                SettingItem.Toggle("Dark mode", false) {},
                SettingItem.Item("Version", "1.0.0"),
                SettingItem.Item("Support", "Contact"),
                SettingItem.Item("Privacy", "Policy"),
                SettingItem.Button("Done") {
                    profileNavigation.navigateUp()
                },
                SettingItem.Button("Logout") {
                    viewModel.logout()
                    val intent = Intent(context, StartActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                }
            )
        )

        binding.rvSetting.adapter = settingsAdapter
        binding.rvSetting.layoutManager = LinearLayoutManager(context)
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).setToolbarProfile()
    }

    companion object {
        fun newInstance(key: String, color: String): Fragment {
            val fragment = SettingFragment()
            val argument = Bundle()
            fragment.arguments = argument
            return fragment
        }
    }

}