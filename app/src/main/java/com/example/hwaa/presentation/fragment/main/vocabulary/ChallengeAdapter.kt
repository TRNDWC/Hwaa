package com.example.hwaa.presentation.fragment.main.vocabulary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hwaa.R
import com.example.hwaa.databinding.ItemChallengeBinding

class ChallengeAdapter : RecyclerView.Adapter<ChallengeAdapter.ChallengeViewHolder>() {
    inner class ChallengeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemChallengeBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_challenge, parent, false)
        return ChallengeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: ChallengeViewHolder, position: Int) {

    }
}
