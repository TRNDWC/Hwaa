package com.example.hwaa.presentation.fragment.main.profile

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hwaa.R
import com.example.hwaa.data.model.UserModel

enum class SettingItemType {
    HEADER,
    ITEM,
    TOGGLE,
    BUTTON,
    AVATAR
}

sealed class SettingItem(val type: SettingItemType) {
    data class Header(val title: String) : SettingItem(SettingItemType.HEADER)
    data class Item(
        val title: String,
        val value: String,
        val onClick: () -> Unit = {},
        val onEdit: (String) -> Unit = {}
    ) : SettingItem(SettingItemType.ITEM)

    data class Toggle(val title: String, val isChecked: Boolean, val onToggle: (Boolean) -> Unit) :
        SettingItem(SettingItemType.TOGGLE)

    data class Button(val title: String, val onClick: () -> Unit) :
        SettingItem(SettingItemType.BUTTON)

    data class Avatar(
        val title: String,
        val uri: String, val onClick: () -> Unit
    ) : SettingItem(SettingItemType.AVATAR)
}

class SettingsAdapter(private var items: ArrayList<SettingItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_ITEM = 1
        const val TYPE_TOGGLE = 2
        const val TYPE_BUTTON = 3
        const val TYPE_AVATAR = 4
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position].type) {
            SettingItemType.HEADER -> TYPE_HEADER
            SettingItemType.ITEM -> TYPE_ITEM
            SettingItemType.TOGGLE -> TYPE_TOGGLE
            SettingItemType.BUTTON -> TYPE_BUTTON
            SettingItemType.AVATAR -> TYPE_AVATAR
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HEADER -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_setting_header, parent, false)
                HeaderViewHolder(view)
            }

            TYPE_ITEM -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_setting, parent, false)
                ItemViewHolder(view)
            }

            TYPE_TOGGLE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_setting_toggle, parent, false)
                ToggleViewHolder(view)
            }

            TYPE_BUTTON -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_setting_button, parent, false)
                ButtonViewHolder(view)
            }

            TYPE_AVATAR -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_setting, parent, false)
                AvatarViewHolder(view)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> holder.bind(items[position] as SettingItem.Header)
            is ItemViewHolder -> holder.bind(items[position] as SettingItem.Item)
            is ToggleViewHolder -> holder.bind(items[position] as SettingItem.Toggle)
            is ButtonViewHolder -> holder.bind(items[position] as SettingItem.Button)
            is AvatarViewHolder -> holder.bind(items[position] as SettingItem.Avatar)
        }
    }

    override fun getItemCount(): Int = items.size

    fun updateAvatar(uri: String) {
        val index = items.indexOfFirst { it is SettingItem.Avatar }
        if (index != -1) {
            items[index].let {
                if (it is SettingItem.Avatar) {
                    items[index] = SettingItem.Avatar("Avatar", uri, it.onClick)
                    notifyItemChanged(index)
                }
            }
        }
    }

    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: SettingItem.Header) {
            itemView.findViewById<TextView>(R.id.headerTitle).text = item.title
        }
    }

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: SettingItem.Item) {
            itemView.findViewById<TextView>(R.id.itemTitle).text = item.title
            itemView.findViewById<EditText>(R.id.itemValue).apply {
                setText(item.value)
                setOnFocusChangeListener { _, hasFocus ->
                    if (!hasFocus) item.onEdit(text.toString())
                }
            }
        }
    }

    class ToggleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: SettingItem.Toggle) {
            val toggle = itemView.findViewById<SwitchCompat>(R.id.toggleSwitch)
            itemView.findViewById<TextView>(R.id.toggleTitle).text = item.title
            toggle.isChecked = item.isChecked
            toggle.setOnCheckedChangeListener { _, isChecked -> item.onToggle(isChecked) }
        }
    }

    class ButtonViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: SettingItem.Button) {
            itemView.findViewById<TextView>(R.id.button).text = item.title
            itemView.setOnClickListener { item.onClick() }
        }
    }

    class AvatarViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: SettingItem.Avatar) {
            itemView.findViewById<ImageView>(R.id.itemImageValue).apply {
                Glide.with(itemView.context).load(item.uri).circleCrop().into(this)
            }
            itemView.setOnClickListener { item.onClick() }
            Glide.with(itemView.context).load(item.uri).circleCrop()
                .into(itemView.findViewById(R.id.itemImageValue))
            itemView.findViewById<TextView>(R.id.itemTitle).text = item.title
            itemView.findViewById<EditText>(R.id.itemValue).visibility = View.GONE
        }
    }
}