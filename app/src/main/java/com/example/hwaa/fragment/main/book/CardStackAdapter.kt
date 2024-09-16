package com.example.hwaa.fragment.main.book

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hwaa.R
import com.example.hwaa.databinding.CvWordCardBinding

class CardStackAdapter(private val cards: List<String>) :
    RecyclerView.Adapter<CardStackAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cv_word_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val card = cards[position]
        holder.binding.tvChineseCharacter.text = card
        holder.binding.tvPinyin.text = card
    }

    override fun getItemCount(): Int = cards.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = CvWordCardBinding.bind(view)
    }
}
