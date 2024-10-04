package com.example.hwaa.util.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.viewbinding.ViewBinding
import com.example.hwaa.databinding.ChipSelectedTagBinding
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
    private var isSelected: Boolean = false,
    var tagType: TagType = TagType.NEW
) : FrameLayout(context, attrs, defStyleAttr) {

    private var binding: ViewBinding? = null

    init {
        binding = when (isSelected) {
            true -> {
                ChipSelectedTagBinding.inflate(LayoutInflater.from(context), this, true)
            }

            false -> {
                ChipTagBinding.inflate(LayoutInflater.from(context), this, true)
            }
        }
    }

    fun setTagText(text: String) {
        when (binding) {
            is ChipTagBinding -> {
                (binding as ChipTagBinding).chipTag.text = text
            }

            is ChipSelectedTagBinding -> {
                (binding as ChipSelectedTagBinding).chipTag.text = text
            }
        }
    }

    var getTagText = when (binding) {
        is ChipTagBinding -> {
            (binding as ChipTagBinding).chipTag.text.toString()
        }

        is ChipSelectedTagBinding -> {
            (binding as ChipSelectedTagBinding).chipTag.text.toString()
        }

        else -> ""
    }

    fun setTagClickListener(listener: OnClickListener) {
        when (binding) {
            is ChipTagBinding -> {
                (binding as ChipTagBinding).chipTag.setOnClickListener(listener)
            }

            is ChipSelectedTagBinding -> {
                (binding as ChipSelectedTagBinding).chipTag.setOnClickListener(listener)
            }
        }
    }


}
