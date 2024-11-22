package com.example.hwaa.presentation.fragment.main.book

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.hwaa.R
import com.example.hwaa.data.model.WordStatModel
import com.example.hwaa.databinding.FragmentLessonBinding
import com.example.hwaa.presentation.activity.main.MainActivity
import com.example.hwaa.presentation.core.base.BaseFragment
import com.example.hwaa.presentation.navigation.book.BookNavigation
import com.example.hwaa.presentation.viewmodel.LessonViewModel
import com.google.android.material.card.MaterialCardView
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction
import com.yuyakaido.android.cardstackview.StackFrom
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@AndroidEntryPoint
class LessonFragment :
    BaseFragment<FragmentLessonBinding, LessonViewModel>(R.layout.fragment_lesson) {

    private val viewModel: LessonViewModel by activityViewModels()
    override fun getVM() = viewModel
    private val activity: MainActivity by lazy { requireActivity() as MainActivity }
    private lateinit var adapter: CardStackAdapter
    private lateinit var cardView: View
    private var isRevised: Boolean? = false
    private var currentWord: WordStatModel? = null


    @Inject
    lateinit var bookNavigation: BookNavigation

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.selectedLesson?.let {
            if (it.isLearned) {
                binding.cardStackView.visibility = View.GONE
                showDialog()
            } else {
                binding.cardStackView.visibility = View.VISIBLE
            }
            viewModel.updateWordList.clear()
            viewModel.updateWordList.addAll(it.lessonModel.words.map { words ->
                WordStatModel.copy(words)
            })
        }
        setUpCard()

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.flowRewind.collect {
                binding.cardStackView.smoothScrollToPosition(0)
                binding.cardStackView.visibility = View.VISIBLE
            }
        }
    }

    private fun setUpCard() {
        val cardStackView = binding.cardStackView

        val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        val toolBarHeight = activity.getBinding().appBarLayout.height
        val bottomBarHeight = activity.getBinding().bottomAppBar.height

        binding.fragmentLesson.layoutParams.height = height - toolBarHeight - bottomBarHeight

        val manager = CardStackLayoutManager(requireContext(), object : CardStackListener {
            override fun onCardDragging(direction: Direction?, ratio: Float) {
                val right = cardView.findViewById<MaterialCardView>(R.id.cv_right)
                val left = cardView.findViewById<MaterialCardView>(R.id.cv_left)
                if (direction == Direction.Right) {
                    right?.alpha = ratio
                    left?.alpha = 0f
                } else if (direction == Direction.Left) {
                    left?.alpha = ratio
                    right?.alpha = 0f
                }
            }

            override fun onCardSwiped(direction: Direction?) {
                if (direction == Direction.Right) {
                    viewModel.updateWordList.find { it.word.id == currentWord?.word?.id }?.apply {
                        isRemembered = true
                        score += 1
                        lastTimeLeaned = System.currentTimeMillis()
                    }
                } else if (direction == Direction.Left) {
                    viewModel.updateWordList.find { it.word.id == currentWord?.word?.id }?.apply {
                        isRemembered = false
                        score -= 1
                        lastTimeLeaned = System.currentTimeMillis()
                    }
                }
                binding.cardStackView.swipe()
            }

            override fun onCardRewound() {
            }

            override fun onCardCanceled() {
                val right = cardView.findViewById<MaterialCardView>(R.id.cv_right)
                val left = cardView.findViewById<MaterialCardView>(R.id.cv_left)
                right?.alpha = 0f
                left?.alpha = 0f
            }

            override fun onCardAppeared(view: View?, position: Int) {
                cardView = view!!
            }

            override fun onCardDisappeared(view: View?, position: Int) {
                val right = view?.findViewById<MaterialCardView>(R.id.cv_right)
                val left = view?.findViewById<MaterialCardView>(R.id.cv_left)
                right?.alpha = 0f
                left?.alpha = 0f
                (requireActivity() as MainActivity).getBinding().toolbar.updateProgress(
                    adapter.itemCount, position == adapter.itemCount
                )
                if (position == adapter.itemCount - 1) {
                    showDialog()
                }
                if (position == 0) {
                    isRevised = false
                }
                currentWord = viewModel.selectedLesson?.lessonModel?.words?.get(position)
            }
        })
        // Customize the card stack behavior
        manager.setStackFrom(StackFrom.Top)
        manager.setVisibleCount(3)
        manager.setTranslationInterval(8.0f)
        manager.setScaleInterval(0.95f)
        manager.setSwipeThreshold(0.3f)
        manager.setMaxDegree(20.0f)
        manager.setDirections(Direction.HORIZONTAL)
        manager.setCanScrollHorizontal(true)
        manager.setCanScrollVertical(false)

        cardStackView.layoutManager = manager
        adapter = CardStackAdapter(getCards())
        cardStackView.adapter = adapter
    }

    private fun showDialog() {
        if (isRevised == true) {
            return
        }
        val directionDialog = DirectionDialog(
            bookNavigation,
            testCallback = {
                isRevised = true
                CoroutineScope(Dispatchers.Main).launch {
                    viewModel.flowRewind.emit(Unit)
                }
            },
            closeCallback = {
                viewModel.updateWord()
                bookNavigation.navigateUp()
            }
        )
        directionDialog.show(childFragmentManager, "DirectionDialog")
    }

    override fun onResume() {
        super.onResume()
        activity.setToolbarLesson()
    }

    private fun getCards(): List<WordStatModel> {
        return viewModel.selectedLesson?.lessonModel?.words ?: emptyList()
    }
}