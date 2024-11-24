package com.example.hwaa.presentation.fragment.main.dictionary

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.webkit.WebViewClient
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.hwaa.R
import com.example.hwaa.presentation.core.base.BaseBottomSheetDialogFragment
import com.example.hwaa.databinding.LayoutDictionaryBinding
import com.example.hwaa.domain.Response
import com.example.hwaa.domain.entity.WordEntity
import com.example.hwaa.presentation.activity.main.MainActivity
import com.example.hwaa.presentation.viewmodel.DictionaryViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class FragmentDictionary :
    BaseBottomSheetDialogFragment<LayoutDictionaryBinding, DictionaryViewModel>() {

    private var word: WordEntity? = null

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

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.flResult.visibility = View.GONE

        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                word = WordEntity()
                word?.wordBasic?.simplified = query?.trim().toString()
                word?.let {
                    viewModel.searchWord(it)
                    setLoadingUI()
                    (activity as MainActivity).showLoading()
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        binding.searchBar.setOnClickListener { binding.searchBar.isIconified = false }

        handleFlow()
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

    @SuppressLint("SetJavaScriptEnabled")
    private fun updateUI(data: WordEntity) {
        binding.apply {
            wordTitle.text = data.wordBasic.simplified
            pronunciation.text = data.wordBasic.entries[0].pinyin
            val definitionText = StringBuilder()
            data.wordBasic.entries[0].definitions.forEach {
                definitionText.append(it).append(", ")
            }
            definition.text = definitionText.toString().subSequence(0, definitionText.length - 2)

            val exampleText = StringBuilder()
            data.example.forEach {
                exampleText.append(it.hanzi).append("\n")
                exampleText.append(it.translation).append("\n\n")
            }
            example.text = exampleText.toString()

            contextualWebview.apply {
                settings.javaScriptEnabled = true
                webViewClient = WebViewClient()
                loadData(
                    WebProvider.getYouGlishWidget(data.wordBasic.simplified),
                    "text/html",
                    "UTF-8"
                )
            }
            (activity as MainActivity).hiddenLoading()
            flResult.visibility = View.VISIBLE
        }
    }

    private fun setLoadingUI() {
        binding.flResult.visibility = View.GONE
    }

    private fun handleFlow() {
        lifecycleScope.launch {
            viewModel.searchFlow.collect { response ->
                when (response) {
                    is Response.Success -> {
                        updateUI(response.data)
                    }

                    is Response.Error -> {
                        Toast.makeText(context, response.exception, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
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