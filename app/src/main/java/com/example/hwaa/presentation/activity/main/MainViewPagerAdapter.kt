package com.example.hwaa.presentation.activity.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.hwaa.presentation.fragment.main.book.BookFragment
import com.example.hwaa.presentation.fragment.main.forum.ForumFragment
import com.example.hwaa.presentation.fragment.main.profile.ProfileFragment
import com.example.hwaa.presentation.fragment.main.vocabulary.VocabularyFragment

class MainViewPagerAdapter(activity: FragmentActivity?) : FragmentStateAdapter(activity!!) {

    private val fragments = arrayOf(
        BookFragment.newInstance("Page # 1", "Page # 1"),
        VocabularyFragment.newInstance("Page # 2", "Page # 2"),
        ForumFragment.newInstance("Page # 3", "Page # 3"),
        ProfileFragment.newInstance("Page # 4", "Page # 4")
    )

    private fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> fragments[0]
            1 -> fragments[1]
            2 -> fragments[2]
            3 -> fragments[3]
            else -> fragments[0]
        }
    }

    companion object {
        private const val NUM_ITEMS = 4
    }

    override fun getItemCount(): Int {
        return NUM_ITEMS
    }

    override fun createFragment(position: Int): Fragment {
        return getItem(position)
    }
}