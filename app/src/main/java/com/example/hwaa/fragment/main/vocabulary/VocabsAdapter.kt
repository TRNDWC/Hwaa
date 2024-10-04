package com.example.hwaa.fragment.main.vocabulary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.hwaa.R
import com.example.hwaa.databinding.ItemVocabRevisionBinding
import com.example.hwaa.util.ui.TagType

class VocabsAdapter(
    private val data: List<String> = listOf()
) : RecyclerView.Adapter<ViewHolder>() {

    inner class ItemVocab(itemView: View) : ViewHolder(itemView) {
        val binding = ItemVocabRevisionBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_vocab_revision, parent, false)
        return ItemVocab(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder as ItemVocab).binding.apply {
            tvChineseCharacter.text = data[position]
            tvPinyin.text = data[position]
        }
    }

    fun filter(tagList: List<TagType>) {
        val filteredData = mutableListOf<String>()
    }
}