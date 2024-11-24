package com.example.hwaa.presentation.fragment.main.vocabulary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hwaa.R
import com.example.hwaa.data.model.TestModel
import com.example.hwaa.databinding.ItemChallengeBinding

interface ChallengeClickListener {
    fun onChallengeClick(challenge: TestModel)
}

class ChallengeAdapter(
    private val listener: ChallengeClickListener
) : RecyclerView.Adapter<ChallengeAdapter.ChallengeViewHolder>() {

    private var data: List<TestModel> = emptyList()

    inner class ChallengeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemChallengeBinding.bind(itemView)

        init {
            binding.btnStartChallenge.setOnClickListener {
                listener.onChallengeClick(data[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_challenge, parent, false)
        return ChallengeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ChallengeViewHolder, position: Int) {
        holder.binding.tvChallengeTitle.text = data[position].name
        holder.binding.tvChallengeDescription.text = data[position].description
        Glide.with(holder.itemView.context)
            .load("https://png.pngtree.com/png-vector/20220520/ourmid/pngtree-challenge-sign-or-stamp-on-white-background-png-image_4706970.png")
            .into(holder.binding.ivChallengeIcon)
    }

    fun updateData(data: List<TestModel>) {
        this.data = data
        notifyDataSetChanged()
    }
}
