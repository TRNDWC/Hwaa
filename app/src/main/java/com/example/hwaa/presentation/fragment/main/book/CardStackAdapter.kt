package com.example.hwaa.presentation.fragment.main.book

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hwaa.R
import com.example.hwaa.data.model.WordStatModel
import com.example.hwaa.databinding.CvWordCardBinding
import com.example.hwaa.presentation.util.ColorPickerProvider
import com.example.hwaa.presentation.util.DimensionUtils

class CardStackAdapter(private val cards: List<WordStatModel>) :
    RecyclerView.Adapter<CardStackAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cv_word_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val card = cards[position]
        val word = card.word
        holder.binding.apply {
            Glide.with(root.context).load(word.image).fitCenter().into(ivWordImage)
            tvChineseCharacter.text = word.hanzi
            tvPinyin.text = word.pinyinTone
            tvMeaning.text = word.translation
            tvExampleSentence.text = word.example
            tvExampleSentenceTranslation.text = word.exampleTranslation
            ColorPickerProvider.getInstance().getMainColorFromImageUrl(
                word.image,
                callback = { color ->
                    if (color != null) {
                        cvLessonFront.strokeColor = color
                        ivWordImage.apply {
                            val borderDrawable = background as LayerDrawable
                            val borderLayer = borderDrawable.getDrawable(0) as GradientDrawable
                            borderLayer.setStroke(
                                DimensionUtils.dpToPx(context, 4).toInt(), color
                            )
                        }
                    }
                }
            )
            cvRight.alpha = 0f
            cvLeft.alpha = 0f
        }
    }

    override fun getItemCount(): Int = cards.size

    fun getTopCardView(context: Context) = ViewHolder(
        LayoutInflater.from(context).inflate(R.layout.cv_word_card, null, false)
    ).binding.root

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = CvWordCardBinding.bind(view)
    }
}
