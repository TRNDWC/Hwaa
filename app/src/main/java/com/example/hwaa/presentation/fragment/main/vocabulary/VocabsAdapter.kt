package com.example.hwaa.presentation.fragment.main.vocabulary

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.hwaa.R
import com.example.hwaa.data.model.WordStatModel
import com.example.hwaa.databinding.ItemVocabRevisionBinding
import com.example.hwaa.presentation.util.ui.TagType
import timber.log.Timber

class VocabsAdapter(
    private val context: Context
) : RecyclerView.Adapter<ViewHolder>() {

    private var data: List<WordStatModel> = listOf()

    inner class ItemVocab(itemView: View) : ViewHolder(itemView) {
        val binding = ItemVocabRevisionBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ItemVocab(
            LayoutInflater.from(parent.context).inflate(R.layout.item_vocab_revision, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is ItemVocab -> {
                holder.binding.tvPinyin.text = data[position].word.pinyinTone
                holder.binding.tvChineseCharacter.text = data[position].word.hanzi
                Glide.with(context).load(data[position].word.image).fitCenter()
                    .into(holder.binding.ivVocabImage)
                holder.binding.tvStatus.text = when (data[position].score) {
                    in -10..2 -> "Need review"
                    in 3..10 -> "Learning"
                    else -> "Mastered"
                }
            }
        }
    }

    fun filter(tagList: List<TagType>) {
        val filteredData = mutableListOf<String>()
    }

    fun updateData(data: List<WordStatModel>) {
        this.data = data
        notifyDataSetChanged()
    }

}