package com.example.hwaa.presentation.fragment.main.vocabulary

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.example.hwaa.R
import com.example.hwaa.databinding.FragmentQuizBinding
import com.example.hwaa.presentation.activity.main.MainActivity
import com.example.hwaa.presentation.core.base.BaseFragment
import com.example.hwaa.presentation.fragment.main.book.ResultDialog
import com.example.hwaa.presentation.navigation.vocabulary.VocabularyNavigation
import com.example.hwaa.presentation.viewmodel.VocabularyViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TestFragment :
    BaseFragment<FragmentQuizBinding, VocabularyViewModel>(R.layout.fragment_quiz) {

    private val viewModel: VocabularyViewModel by activityViewModels()
    override fun getVM() = viewModel
    private var position = 0
    private var score = 0

    @Inject
    lateinit var vocabularyNavigation: VocabularyNavigation

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        position = 0
        showQuiz()
        binding.ivBack.setOnClickListener {
            vocabularyNavigation.navigateUp()
        }
    }

    private fun showQuiz() {
        bindData(position)
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as MainActivity).setToolbarQuiz()
    }

    private fun bindData(position: Int) {
        binding.apply {
            val lessonStatModel = viewModel.selectedChallenge
            lessonStatModel!!.words[position].word.question.let {
                tvQuestion.text = it.question
                tvAnswer1.text = it.options[0]
                tvAnswer2.text = it.options[1]
                tvAnswer3.text = it.options[2]
                tvAnswer4.text = it.options[3]
            }
            tvAnswer1.setOnClickListener {
                handleShowResult("1")
            }
            tvAnswer2.setOnClickListener {
                handleShowResult("2")
            }
            tvAnswer3.setOnClickListener {
                handleShowResult("3")
            }
            tvAnswer4.setOnClickListener {
                handleShowResult("4")
            }
            tvQuestionNumber.text =
                "Question ${position + 1} of ${lessonStatModel.words.size}"
        }
    }

    private fun handleShowResult(selectedAnswer: String) {
        if (position >= viewModel.selectedChallenge!!.words.size) {
            return
        }
        val lessonStatModel = viewModel.selectedChallenge
        val correctAnswer =
            lessonStatModel!!.words[position].word.question.answer // Get the correct answer

        if (selectedAnswer == correctAnswer) {
            showFeedback(true, selectedAnswer.toInt() - 1)
            viewModel.updateWordList[position].apply {
                score += 1
            }
        } else {
            showFeedback(false, selectedAnswer.toInt() - 1)
            viewModel.updateWordList[position].apply {
                score -= 1
            }
        }

        position++
        if (position < lessonStatModel.words.size) {
            binding.root.postDelayed({ showQuiz() }, 1000)
        }
    }

    private fun showFeedback(isCorrect: Boolean, index: Int) {
        if (isAdded) {
            val title = if (isCorrect) "Correct!" else "Incorrect!"
            val message = if (isCorrect) {
                "You have selected the correct answer!"
            } else {
                "You have selected the incorrect answer!.\nThe correct answer is ${viewModel.selectedChallenge!!.words[position].word.question.options[index]}"
            }
            score += if (isCorrect) 1 else 0
            showBottomSheetDialog(title, message)
        }
    }

    @SuppressLint("MissingInflatedId")
    fun showBottomSheetDialog(title: String, message: String) {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.layout_bottom_sheet, null)
        view.findViewById<TextView>(R.id.tvTitle).text = title
        view.findViewById<TextView>(R.id.tvDescription).text = message
        view.findViewById<Button>(R.id.btnClose).setOnClickListener {
            bottomSheetDialog.dismiss()
            if (position == viewModel.selectedChallenge!!.words.size) {
                showEndQuizDialog(score)
            }
        }
        bottomSheetDialog.setContentView(view)
        bottomSheetDialog.show()
    }

    private fun showEndQuizDialog(score: Int) {
        viewModel.updateWordStatList()
        val resultDialog = ResultDialog(score, vocabularyNavigation)
        resultDialog.show(childFragmentManager, "ResultDialog")
    }
}