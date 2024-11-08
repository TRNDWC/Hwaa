package com.example.hwaa.presentation.util.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.hwaa.databinding.ChipTagBinding

enum class TagType(s: String) {
    MASTERED("mastered"),
    IN_PROGRESS("in progress"),
    NEED_PRACTICE("need practice"),
    NEW("new"),
    VERB("verb"),
    NOUN("noun"),
    ADJECTIVE("adjective"),
    ADVERB("adverb")
}

class VocabTag @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    private var tagType: TagType = TagType.NEW
) : FrameLayout(context, attrs, defStyleAttr) {

    fun isChecked() = binding.chipTag.isChecked
    private var binding: ChipTagBinding = ChipTagBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        binding.chipTag.text = tagType.name
    }
}
