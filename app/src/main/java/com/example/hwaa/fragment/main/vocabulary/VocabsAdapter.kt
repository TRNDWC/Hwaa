package com.example.hwaa.fragment.main.vocabulary

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar.GONE
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.hwaa.R
import com.example.hwaa.databinding.ItemChallengeBinding
import com.example.hwaa.databinding.ItemChallengeListBinding
import com.example.hwaa.databinding.ItemVocabRevisionBinding
import com.example.hwaa.databinding.TbVocabFragmentBinding
import com.example.hwaa.util.ui.TagType
import com.example.hwaa.util.ui.VocabTag

class VocabsAdapter(
    private val data: List<String> = listOf(),
    private val context: Context
) : RecyclerView.Adapter<ViewHolder>() {

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
                holder.binding.tvPinyin.text = data[position]
                holder.binding.tvChineseCharacter.text = data[position]
            }
        }
    }

    fun filter(tagList: List<TagType>) {
        val filteredData = mutableListOf<String>()
    }

}