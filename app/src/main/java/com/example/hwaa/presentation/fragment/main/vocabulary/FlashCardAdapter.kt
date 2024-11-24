package com.example.hwaa.presentation.fragment.main.vocabulary

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hwaa.R
import com.example.hwaa.data.model.WordStatModel
import com.example.hwaa.databinding.CvFlashCardBinding

class FlashCardAdapter(private val cards: List<WordStatModel>, private val context: Context) :
    RecyclerView.Adapter<FlashCardAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.cv_flash_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val card = cards[position]
        holder.binding.apply {
            tvChineseCharacter.text = card.word.hanzi
            tvPinyin.text = card.word.pinyinTone
            tvMeaning.text = card.word.translation
            tvExampleSentence.text = card.word.example
        }
        Glide.with(context).load(card.word.image).into(holder.binding.ivWordImage)

    }

    override fun getItemCount(): Int = cards.size

    fun updateData(data: List<WordStatModel>) {

    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = CvFlashCardBinding.bind(view)
        private var isFront = true

        init {
            binding.cvLessonFront.setOnClickListener {
                flipCard()
            }
            binding.cvLessonBack.setOnClickListener {
                flipCard()
            }

            view.alpha = 0f
        }

        private fun flipCard() {
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
}
