package com.example.hwaa.fragment.main.vocabulary

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.fragment.app.viewModels
import com.example.hwaa.R
import com.example.hwaa.activity.main.MainActivity
import com.example.hwaa.core.base.BaseFragment
import com.example.hwaa.databinding.FragmentFlashCardBinding
import com.example.hwaa.viewmodel.VocabularyViewModel
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction
import com.yuyakaido.android.cardstackview.StackFrom
import com.yuyakaido.android.cardstackview.SwipeableMethod
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FlashCardFragment :
    BaseFragment<FragmentFlashCardBinding, VocabularyViewModel>(R.layout.fragment_flash_card) {

    private val viewModel: VocabularyViewModel by viewModels()
    override fun getVM() = viewModel
    private lateinit var adapter: FlashCardAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpCard()
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).setToolbarFlashCard()
    }

    private fun setUpCard() {
        val cardStackView = binding.cardStackView
        val displayMetrics = DisplayMetrics()
        (activity as MainActivity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        val toolBarHeight = (activity as MainActivity).getBinding().appBarLayout.height
        val bottomBarHeight = (activity as MainActivity).getBinding().bottomAppBar.height
        binding.fragmentFlashCard.layoutParams.height = height - toolBarHeight - bottomBarHeight
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
                view?.animate()?.alpha(1f)?.translationZ(0f)?.setDuration(500)
                    ?.setInterpolator(LinearInterpolator())
                    ?.start()
            }

            override fun onCardDisappeared(view: View?, position: Int) {
                view?.animate()?.alpha(0f)?.translationZ(-1f)?.setDuration(500)
                    ?.setInterpolator(LinearInterpolator())
                    ?.start()
            }
        })

        manager.setStackFrom(StackFrom.None)
        manager.setVisibleCount(1)
        manager.setTranslationInterval(8.0f)
        manager.setScaleInterval(0.95f)
        manager.setSwipeThreshold(0.3f)
        manager.setMaxDegree(20.0f)
        manager.setDirections(Direction.HORIZONTAL)
        manager.setCanScrollHorizontal(true)
        manager.setCanScrollVertical(false)

        cardStackView.layoutManager = manager

        adapter = FlashCardAdapter(
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
        binding.cardStackView.adapter = adapter
    }
}