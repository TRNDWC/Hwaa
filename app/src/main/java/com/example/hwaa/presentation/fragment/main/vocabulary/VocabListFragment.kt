package com.example.hwaa.presentation.fragment.main.vocabulary

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hwaa.R
import com.example.hwaa.presentation.core.base.BaseFragment
import com.example.hwaa.databinding.FragmentVocabListBinding
import com.example.hwaa.presentation.navigation.vocabulary.VocabularyNavigation
import com.example.hwaa.presentation.activity.main.MainActivity
import com.example.hwaa.presentation.util.ui.BounceEdgeEffectFactory
import com.example.hwaa.presentation.util.ui.TagClickListener
import com.example.hwaa.presentation.util.ui.TagType
import com.example.hwaa.presentation.util.ui.VocabTag
import com.example.hwaa.presentation.viewmodel.VocabularyViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class VocabListFragment :
    BaseFragment<FragmentVocabListBinding, VocabularyViewModel>(R.layout.fragment_vocab_list),
    TagClickListener {

    @Inject
    lateinit var vocabularyNavigation: VocabularyNavigation
    private val viewModel: VocabularyViewModel by viewModels()
    private lateinit var vocabAdapter: VocabsAdapter
    private lateinit var challengeAdapter: ChallengeAdapter
    private val tagList: MutableList<TagType> = mutableListOf()

    override fun getVM() = viewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).getBinding().toolbar.tagClickListener =
            this
        vocabAdapter = VocabsAdapter(
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
            ), requireContext()
        )
        binding.rvVocabs.adapter = vocabAdapter
        binding.rvVocabs.layoutManager = GridLayoutManager(context, 2)
        binding.rvVocabs.edgeEffectFactory = BounceEdgeEffectFactory()

        binding.icChallengeList.challengesRecyclerView.apply {
            adapter = ChallengeAdapter()
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        }

        binding.icChallengeList.chipGroup.apply {
            addView(VocabTag(context, tagType = TagType.MASTERED))
            addView(VocabTag(context, tagType = TagType.IN_PROGRESS))
            addView(VocabTag(context, tagType = TagType.NEED_PRACTICE))
            addView(VocabTag(context, tagType = TagType.NEW))
            addView(VocabTag(context, tagType = TagType.VERB))
            addView(VocabTag(context, tagType = TagType.NOUN))
            addView(VocabTag(context, tagType = TagType.ADJECTIVE))
            addView(VocabTag(context, tagType = TagType.ADVERB))
        }

    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).setToolbarVocab()
    }

    override fun tagSelectedListener(tagType: TagType) {
        if (tagList.contains(tagType)) {
            tagList.remove(tagType)
        } else {
            tagList.add(tagType)
        }
        vocabAdapter.filter(tagList)
    }

    override fun startReviseListener() {
        val dialogFragment: DialogFragment = CardSettingDialog(vocabularyNavigation)
        dialogFragment.show(childFragmentManager, "CardSettingDialog")
    }

    override fun backFromFlashCard() {
        vocabularyNavigation.navigateUp()
    }
}