package com.example.hwaa.fragment.main.book

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hwaa.R
import com.example.hwaa.core.base.BaseFragment
import com.example.hwaa.databinding.FragmentBookBinding

class BookFragment : BaseFragment<FragmentBookBinding, BookViewModel>(R.layout.fragment_book), BookItemCallback {

    val viewModel: BookViewModel by viewModels()
    private val adapter : BookAdapter by lazy { BookAdapter(this) }
    override fun getVM() = viewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvBook.adapter = adapter
        binding.rvBook.layoutManager = LinearLayoutManager(context)
    }

    override fun onItemClicked(position: Int) {
        //TODO
    }

    override fun onBookmarkClicked(position: Int, view: View) {
        view.isSelected = !view.isSelected
    }

}