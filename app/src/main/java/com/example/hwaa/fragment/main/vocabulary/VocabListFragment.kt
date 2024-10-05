package com.example.hwaa.fragment.main.vocabulary

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hwaa.R
import com.example.hwaa.core.base.BaseFragment
import com.example.hwaa.databinding.FragmentVocabListBinding
import com.example.hwaa.navigation.vocabulary.VocabularyNavigation
import com.example.hwaa.util.ui.BounceEdgeEffectFactory
import com.example.hwaa.util.ui.TagClickListener
import com.example.hwaa.util.ui.TagType
import com.example.hwaa.viewmodel.VocabularyViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class VocabListFragment :
    BaseFragment<FragmentVocabListBinding, VocabularyViewModel>(R.layout.fragment_vocab_list),
    TagClickListener {

    @Inject
    lateinit var vocabularyNavigation: VocabularyNavigation
    private val viewModel: VocabularyViewModel by viewModels()
    private val adapter = VocabsAdapter(
        listOf(
            "你好",
            "你好吗",
            "我很好",
            "你呢",
            "我也很好",
            "再见",
            "谢谢",
            "不客气",
            "对不起",
            "没关系",
            "请问",
            "请坐",
            "请喝茶",
            "请吃饭"
        )
    )
    private val tagList: MutableList<TagType> = mutableListOf()

    override fun getVM() = viewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as com.example.hwaa.activity.main.MainActivity).getBinding().toolbar.tagClickListener =
            this
        binding.rvVocabs.adapter = adapter
        binding.rvVocabs.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.rvVocabs.edgeEffectFactory = BounceEdgeEffectFactory()
    }

    override fun onResume() {
        super.onResume()
        (activity as com.example.hwaa.activity.main.MainActivity).setToolbarVocab()
    }

    override fun tagSelectedListener(tagType: TagType) {
        if (tagList.contains(tagType)) {
            tagList.remove(tagType)
        } else {
            tagList.add(tagType)
        }
        adapter.filter(tagList)
    }

    override fun startReviseListener() {
//        val dialogFragment: DialogFragment = CardSettingDialog()
//        dialogFragment.show(childFragmentManager, "CardSettingDialog")
        vocabularyNavigation.fromVocabularyToFlashCard()
    }

    override fun backFromFlashCard() {
        vocabularyNavigation.navigateUp()
    }
}