package com.example.hwaa.fragment.main

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import com.example.hwaa.R
import com.example.hwaa.core.base.BaseBottomSheetDialogFragment
import com.example.hwaa.databinding.LayoutDictionaryBinding
import com.example.hwaa.viewmodel.DictionaryViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import timber.log.Timber

class FragmentDictionary :
    BaseBottomSheetDialogFragment<LayoutDictionaryBinding, DictionaryViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.layout_dictionary
    }

    private val viewModel: DictionaryViewModel by viewModels()
    override fun getVM() = viewModel

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
        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Timber.d("onQueryTextSubmit: $query")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Timber.d("onQueryTextChange: $newText")
                return false
            }
        })

        binding.searchBar.setOnClickListener { binding.searchBar.isIconified = false }
    }

    private fun getWindowHeight() = resources.displayMetrics.heightPixels

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener {
            val view: FrameLayout = dialog.findViewById(R.id.design_bottom_sheet)
            view.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
            val behavior = BottomSheetBehavior.from(view)
            behavior.peekHeight = getWindowHeight()
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
        return dialog
    }

    override fun onResume() {
        super.onResume()
        view?.let {
            val searchView: SearchView = binding.searchBar
        }
    }

    companion object {
        fun newInstance() = FragmentDictionary()
    }
}