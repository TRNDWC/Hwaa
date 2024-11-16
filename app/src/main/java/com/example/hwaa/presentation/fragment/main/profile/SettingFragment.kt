package com.example.hwaa.presentation.fragment.main.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hwaa.R
import com.example.hwaa.presentation.activity.main.MainActivity
import com.example.hwaa.presentation.core.base.BaseFragment
import com.example.hwaa.databinding.FragmentSettingBinding
import com.example.hwaa.domain.Response
import com.example.hwaa.presentation.activity.start.StartActivity
import com.example.hwaa.presentation.navigation.profile.ProfileNavigation
import com.example.hwaa.presentation.util.ImagePickerProvider
import com.example.hwaa.presentation.util.PermissionProvider
import com.example.hwaa.presentation.util.PermissionsResultCallback
import com.example.hwaa.presentation.util.UserProvider
import com.example.hwaa.presentation.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SettingFragment :
    BaseFragment<FragmentSettingBinding, ProfileViewModel>(R.layout.fragment_setting),
    PermissionsResultCallback {

    private val viewModel: ProfileViewModel by viewModels()
    override fun getVM() = viewModel
    private lateinit var settingsAdapter: SettingsAdapter
    private val userModel = UserProvider.getUser()
    private val imagePickerLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            ImagePickerProvider.handleResult(result) { uri ->
                if (uri != null) {
                    settingsAdapter.updateAvatar(uri.toString())
                    userModel.profileImage = uri.toString()
                } else {
                    Toast.makeText(context, "Failed to pick image", Toast.LENGTH_SHORT).show()
                }
            }
        }

    @Inject
    lateinit var profileNavigation: ProfileNavigation

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settingsAdapter = SettingsAdapter(
            arrayListOf(
                SettingItem.Header("Account"),
                SettingItem.Item("Username", userModel.displayName) {
                    userModel.displayName = it
                    Toast.makeText(context, "Username updated ${it}", Toast.LENGTH_SHORT).show()
                },
                SettingItem.Avatar(
                    "Avatar",
                    userModel.profileImage,
                ) {
                    PermissionProvider.checkPermission(
                        requireActivity(),
                        PermissionProvider.permissions[0],
                        this
                    )
                },
                SettingItem.Item("Email", userModel.email),
                SettingItem.Header("General"),
                SettingItem.Toggle("Notification", true) {},
                SettingItem.Item("Language", "English"),
                SettingItem.Toggle("Dark mode", false) {},
                SettingItem.Item("Version", "1.0.0"),
                SettingItem.Item("Support", "Contact"),
                SettingItem.Item("Privacy", "Policy"),
                SettingItem.Button("Done") {
                    viewModel.updateProfile(userModel)
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

        lifecycleScope.launch {
            viewModel.updateFlow.collect {
                when (it) {
                    is Response.Success -> {
                        profileNavigation.navigateUp()
                        UserProvider.saveUser(userModel)
                    }

                    is Response.Error -> {
                        Toast.makeText(context, it.exception, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    companion object {
        fun newInstance(key: String, color: String): Fragment {
            val fragment = SettingFragment()
            val argument = Bundle()
            fragment.arguments = argument
            return fragment
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (grantResults.isNotEmpty() && grantResults[0] == android.content.pm.PackageManager.PERMISSION_GRANTED) {
            ImagePickerProvider.pickImage(
                requireActivity(),
                imagePickerLauncher,
                crop = true,
                compressSize = 1024,
                maxWidth = 1080,
                maxHeight = 1080
            )
        }
    }

}