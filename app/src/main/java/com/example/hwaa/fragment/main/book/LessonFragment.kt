package com.example.hwaa.fragment.main.book

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.airbnb.lottie.LottieAnimationView
import com.example.hwaa.R
import com.example.hwaa.activity.main.MainActivity
import com.example.hwaa.core.base.BaseFragment
import com.example.hwaa.databinding.FragmentLessonBinding
import com.example.hwaa.navigation.book.BookNavigation
import com.example.hwaa.viewmodel.LessonViewModel
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction
import com.yuyakaido.android.cardstackview.StackFrom
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class LessonFragment :
    BaseFragment<FragmentLessonBinding, LessonViewModel>(R.layout.fragment_lesson) {

    private val viewModel: LessonViewModel by viewModels()
    override fun getVM() = viewModel
    private val activity: MainActivity by lazy { requireActivity() as MainActivity }
    private lateinit var adapter: CardStackAdapter


    @Inject
    lateinit var bookNavigation: BookNavigation

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val animationView
                : LottieAnimationView = binding.animationView
        animationView
            .addAnimatorUpdateListener { animation: ValueAnimator? ->
                val progress = (animation?.animatedValue as Float * 100).toInt()
                if (progress == 100) {
                    animationView
                        .pauseAnimation()
                    animationView
                        .visibility = View.GONE
                }
            }
        binding.ivPlay.setOnClickListener {
            animationView
                .visibility = View.VISIBLE
            animationView
                .playAnimation()
        }

        setUpCard()
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
            }

            override fun onCardSwiped(direction: Direction?) {
                if (direction == Direction.Right || direction == Direction.Left) {
                    binding.cardStackView.swipe()
                }
            }

            override fun onCardRewound() {
            }

            override fun onCardCanceled() {
            }

            override fun onCardAppeared(view: View?, position: Int) {
            }

            override fun onCardDisappeared(view: View?, position: Int) {
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

    override fun onResume() {
        super.onResume()
        activity.setToolbarLesson()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun setOnClick() {
        super.setOnClick()
        binding.let {
        }
    }

    private fun getCards(): List<String> {
        return listOf(
            "1",
            "2",
            "3",
            "4",
            "5",
            "6",
            "7",
            "8",
            "9",
            "10"
        )
    }
}