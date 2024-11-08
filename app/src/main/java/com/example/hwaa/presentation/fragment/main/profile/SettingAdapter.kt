package com.example.hwaa.presentation.fragment.main.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.hwaa.R

enum class SettingItemType {
    HEADER,
    ITEM,
    TOGGLE,
    BUTTON
}

sealed class SettingItem(val type: SettingItemType) {
    data class Header(val title: String) : SettingItem(SettingItemType.HEADER)
    data class Item(val title: String, val value: String) : SettingItem(SettingItemType.ITEM)
    data class Toggle(val title: String, val isChecked: Boolean, val onToggle: (Boolean) -> Unit) :
        SettingItem(SettingItemType.TOGGLE)

    data class Button(val title: String, val onClick: () -> Unit) :
        SettingItem(SettingItemType.BUTTON)
}

class SettingsAdapter(private val items: List<SettingItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_ITEM = 1
        const val TYPE_TOGGLE = 2
        const val TYPE_BUTTON = 3
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position].type) {
            SettingItemType.HEADER -> TYPE_HEADER
            SettingItemType.ITEM -> TYPE_ITEM
            SettingItemType.TOGGLE -> TYPE_TOGGLE
            SettingItemType.BUTTON -> TYPE_BUTTON
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

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> holder.bind(items[position] as SettingItem.Header)
            is ItemViewHolder -> holder.bind(items[position] as SettingItem.Item)
            is ToggleViewHolder -> holder.bind(items[position] as SettingItem.Toggle)
            is ButtonViewHolder -> holder.bind(items[position] as SettingItem.Button)
        }
    }

    override fun getItemCount(): Int = items.size

    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: SettingItem.Header) {
            itemView.findViewById<TextView>(R.id.headerTitle).text = item.title
        }
    }

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: SettingItem.Item) {
            itemView.findViewById<TextView>(R.id.itemTitle).text = item.title
            itemView.findViewById<TextView>(R.id.itemValue).text = item.value
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
}