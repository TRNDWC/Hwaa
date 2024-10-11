package com.example.hwaa.fragment.main.vocabulary

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.hwaa.R
import com.example.hwaa.databinding.ItemChallengeBinding
import com.example.hwaa.databinding.ItemChallengeListBinding
import com.example.hwaa.databinding.ItemVocabRevisionBinding
import com.example.hwaa.util.ui.TagType

enum class VocabType {
    VOCAB, CHALLENGE
}

class VocabsAdapter(
    private val data: List<String> = listOf(),
    private val context: Context
) : RecyclerView.Adapter<ViewHolder>() {

    inner class ItemVocab(itemView: View) : ViewHolder(itemView) {
        val binding = ItemVocabRevisionBinding.bind(itemView)
    }

    inner class ItemChallenge(itemView: View) : ViewHolder(itemView) {
        val binding = ItemChallengeListBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            VocabType.VOCAB.ordinal -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_vocab_revision, parent, false)
                ItemVocab(view)
            }

            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_challenge_list, parent, false)
                ItemChallenge(view)
            }
        }
    }

    override fun getItemCount(): Int {
        return createDataList().size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is ItemVocab -> {
                holder.binding.tvPinyin.text = data[position - 1]
                holder.binding.tvChineseCharacter.text = data[position - 1]
            }

            is ItemChallenge -> {
                val challengeAdapter = ChallengeAdapter()
                holder.binding.challengesRecyclerView.adapter = challengeAdapter
                holder.binding.challengesRecyclerView.layoutManager =
                    LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return createDataList()[position].ordinal
    }

    fun filter(tagList: List<TagType>) {
        val filteredData = mutableListOf<String>()
    }

    private fun createDataList(): List<VocabType> {
        val dataList = mutableListOf<VocabType>()
        dataList.add(VocabType.CHALLENGE)
        for (i in data.indices) {
            dataList.add(VocabType.VOCAB)
        }
        return dataList
    }
}