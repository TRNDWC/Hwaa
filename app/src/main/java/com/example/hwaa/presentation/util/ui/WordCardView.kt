package com.example.hwaa.presentation.util.ui

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.hwaa.R
import com.example.hwaa.databinding.CvWordCardBinding

class WordCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): ConstraintLayout(context, attrs, defStyleAttr) {
    val binding: CvWordCardBinding = CvWordCardBinding.inflate(
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater,
        this,
        true
    )

    fun setContent (a : String) {
        binding.tvChineseCharacter.text = a
    }

    private var isFront = true
    fun flipCard() {
        val setOut =
            AnimatorInflater.loadAnimator(context, R.animator.card_flip_out) as AnimatorSet
        val setIn =
            AnimatorInflater.loadAnimator(context, R.animator.card_flip_in) as AnimatorSet

        setOut.setTarget(if (isFront) binding.cvLessonFront else binding.cvLessonBack)
        setIn.setTarget(if (isFront) binding.cvLessonBack else binding.cvLessonFront)

        setOut.start()
        setIn.start()

        isFront = !isFront
    }
}