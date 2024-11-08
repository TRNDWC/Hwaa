package com.example.hwaa.presentation.core.base

import android.os.Bundle
import androidx.databinding.ViewDataBinding

abstract class BaseBottomSheetDialogFragment<BD : ViewDataBinding, VM : BaseViewModel> :
    BaseBottomSheetDialogFragmentNotViewModel<BD>() {

    private lateinit var viewModel: VM

    abstract fun getVM(): VM

    abstract override fun getLayoutId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getVM()
    }
}