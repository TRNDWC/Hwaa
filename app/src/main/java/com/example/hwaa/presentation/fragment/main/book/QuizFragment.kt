package com.example.hwaa.presentation.fragment.main.book

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.hwaa.R
import com.example.hwaa.data.model.LessonStatModel
import com.example.hwaa.databinding.FragmentQuizBinding
import com.example.hwaa.domain.entity.WordStatEntity
import com.example.hwaa.presentation.core.base.BaseFragment
import com.example.hwaa.presentation.navigation.book.BookNavigation
import com.example.hwaa.presentation.viewmodel.LessonViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class QuizFragment : BaseFragment<FragmentQuizBinding, LessonViewModel>(R.layout.fragment_quiz) {

    private val viewModel: LessonViewModel by activityViewModels()
    override fun getVM() = viewModel
    private var position = 0
    private var score = 0

    @Inject
    lateinit var bookNavigation: BookNavigation

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        position = 0
        showQuiz()
    }

    private fun showQuiz() {
        bindData(position)
    }

    private fun bindData(position: Int) {
        binding.apply {
            val lessonStatModel = viewModel.selectedLesson
            lessonStatModel!!.lessonModel.words[position].word.question.let {
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
        }
    }

    private fun handleShowResult(selectedAnswer: String) {
        if (position >= viewModel.selectedLesson!!.lessonModel.words.size) {
            return
        }
        val lessonStatModel = viewModel.selectedLesson
        val correctAnswer =
            lessonStatModel!!.lessonModel.words[position].word.question.answer // Get the correct answer

        // Check if the selected answer is correct
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
        if (position < lessonStatModel.lessonModel.words.size) {
            binding.root.postDelayed({ showQuiz() }, 1000)
        }
    }

    private fun showFeedback(isCorrect: Boolean, index: Int) {
        if (isAdded) {
            val title = if (isCorrect) "Correct!" else "Incorrect!"
            val message = if (isCorrect) {
                "You have selected the correct answer!"
            } else {
                "You have selected the incorrect answer!.\nThe correct answer is ${viewModel.selectedLesson!!.lessonModel.words[position].word.question.options[index]}"
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
            if (position == viewModel.selectedLesson!!.lessonModel.words.size) {
                showEndQuizDialog(score)
            }
        }
        bottomSheetDialog.setContentView(view)
        bottomSheetDialog.show()
    }

    private fun showEndQuizDialog(score: Int) {
        viewModel.updateLesson(score)
        val resultDialog = ResultDialog(score, bookNavigation)
        resultDialog.show(childFragmentManager, "ResultDialog")
    }
}