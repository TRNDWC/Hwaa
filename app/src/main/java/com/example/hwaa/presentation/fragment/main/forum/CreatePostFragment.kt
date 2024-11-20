package com.example.hwaa.presentation.fragment.main.forum

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.hwaa.R
import com.example.hwaa.data.model.BlogModel
import com.example.hwaa.presentation.core.base.BaseBottomSheetDialogFragment
import com.example.hwaa.databinding.LayoutCreatePostBinding
import com.example.hwaa.domain.Response
import com.example.hwaa.presentation.activity.main.MainActivity
import com.example.hwaa.presentation.util.ImagePickerProvider
import com.example.hwaa.presentation.util.UserProvider
import com.example.hwaa.presentation.viewmodel.ForumViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreatePostFragment :
    BaseBottomSheetDialogFragment<LayoutCreatePostBinding, ForumViewModel>() {


    private val viewModel: ForumViewModel by viewModels()
    private val userModel = UserProvider.getUser()
    override fun getVM(): ForumViewModel {
        return viewModel
    }

    private val blogModel = BlogModel()

    private val imagePickerLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            ImagePickerProvider.handleResult(result) { uri ->
                if (uri != null) {
                    Glide.with(requireContext())
                        .load(uri)
                        .centerCrop()
                        .into(binding.ivPost)
                    blogModel.image = uri.toString()
                    binding.ivPost.visibility = View.VISIBLE
                } else {
                    Toast.makeText(context, "Failed to pick image", Toast.LENGTH_SHORT).show()
                }
            }
        }

    override fun getLayoutId(): Int {
        return R.layout.layout_create_post
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.etContent.requestFocus()
        binding.tbCreate.ibBack.setOnClickListener { dismiss() }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener {
            val bottomSheet =
                dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            val behavior =
                BottomSheetBehavior.from(bottomSheet)
            bottomSheet.setBackgroundColor(resources.getColor(android.R.color.transparent, null))
            bottomSheet.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
            behavior.skipCollapsed = true
            behavior.state =
                BottomSheetBehavior.STATE_EXPANDED
        }
        return dialog
    }

    override fun onResume() {
        super.onResume()
        view?.let {
            val editText: EditText = binding.etContent

            editText.post {
                val inputMethodManager =
                    requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
            }
        }

        binding.apply {
            btnPost.setOnClickListener {
                blogModel.content = etContent.text.toString()
                blogModel.author = userModel
                blogModel.date = System.currentTimeMillis().toString()
                viewModel.postBlog(blogModel)
                (requireActivity() as MainActivity).showLoading()
            }
            Glide.with(requireContext())
                .load(userModel.profileImage)
                .circleCrop()
                .into(ivAvatar)

            tvName.text = userModel.displayName

            tbCreate.ibBookmark.apply {
                setImageResource(R.drawable.ic_camera)
                setOnClickListener {
                    ImagePickerProvider.pickImage(
                        requireActivity(),
                        imagePickerLauncher
                    )
                }
            }
        }
        handleFlow()
    }

    private fun handleFlow() {
        lifecycleScope.launch {
            viewModel.postBlogFlow.collect { response ->
                when (response) {
                    is Response.Success -> {
                        (requireActivity() as MainActivity).hiddenLoading()
                        dismiss()
                    }

                    is Response.Error -> {
                        (requireActivity() as MainActivity).hiddenLoading()
                        Toast.makeText(context, response.exception, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
