package com.example.hwaa.fragment.main.forum

import android.app.Dialog
import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.viewModels
import com.example.hwaa.databinding.LayoutCreatePostBinding
import com.example.hwaa.viewmodel.ForumViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class CreatePostFragment : BottomSheetDialogFragment() {
    private val viewModel: ForumViewModel by viewModels()
    private lateinit var binding: LayoutCreatePostBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LayoutCreatePostBinding.inflate(inflater, container, false)
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return binding.root
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
    }
}
