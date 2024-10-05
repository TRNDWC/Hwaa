package com.example.hwaa.activity.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.hwaa.R
import com.example.hwaa.core.base.BaseActivity
import com.example.hwaa.databinding.ActivityMainBinding
import com.example.hwaa.navigation.AppNavigation
import com.example.hwaa.navigation.book.BookNavigation
import com.example.hwaa.navigation.forum.ForumNavigation
import com.example.hwaa.util.ui.HwaaToolBarCallBack
import com.example.hwaa.util.ui.TagType
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.navigation.NavigationBarView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(), HwaaToolBarCallBack {

    @Inject
    lateinit var appNavigation: AppNavigation

    @Inject
    lateinit var bookNavigation: BookNavigation

    @Inject
    lateinit var forumNavigation: ForumNavigation

    private val viewModel: MainViewModel by viewModels()
    override fun getVM() = viewModel
    override val layoutId = R.layout.activity_main

    fun getBinding() = binding

    private val navigationBarView = NavigationBarView.OnItemSelectedListener { item ->
        when (item.itemId) {
            R.id.bookFragment -> {
                binding.viewPager.setCurrentItem(0, false)
                return@OnItemSelectedListener true
            }

            R.id.vocabularyFragment -> {
                binding.viewPager.setCurrentItem(1, false)
                return@OnItemSelectedListener true
            }

            R.id.forumFragment -> {
                binding.viewPager.setCurrentItem(2, false)
                return@OnItemSelectedListener true
            }

            R.id.profileFragment -> {
                binding.viewPager.setCurrentItem(3, false)
                return@OnItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.bottomNavigationView.apply {
            background = null
            setOnItemSelectedListener(navigationBarView)
        }
        setUpViewPagerAdapter()
        binding.fab.setOnClickListener {
            Toast.makeText(this, "Floating Action Button Clicked", Toast.LENGTH_SHORT).show()
        }
        binding.toolbar.callBack = this
    }

    fun setToolbarProfile() {
        hideAppBar()
        setAppBarHideOnScroll(true)
        binding.toolbar.inflateToolBarLayout(
            collapsingToolbarLayout = binding.collapsingToolbarLayout
        )
    }

    private fun setUpViewPagerAdapter() {
        binding.viewPager.adapter = MainViewPagerAdapter(this)
        binding.viewPager.setOffscreenPageLimit(4)
        binding.viewPager.isUserInputEnabled = false
    }

    fun setToolbarLesson() {
        showAppBar()
        setAppBarHideOnScroll(false)
        binding.toolbar.inflateToolBarLayout(
            R.layout.tb_lesson_fragment,
            binding.collapsingToolbarLayout
        )
        setAppBarHideOnScroll(false)
    }

    fun setToolbarForum() {
        setAppBarHideOnScroll(true)
        showAppBar()
        binding.toolbar.inflateToolBarLayout(
            R.layout.tb_forum_fragment,
            binding.collapsingToolbarLayout
        )
        setAppBarHideOnScroll(true)
    }

    fun setToolbarLessons() {
        setAppBarHideOnScroll(true)
        showAppBar()
        binding.toolbar.inflateToolBarLayout(
            R.layout.tb_book_fragment,
            binding.collapsingToolbarLayout
        )
    }

    fun setToolbarDetailPost() {
        setAppBarHideOnScroll(false)
        showAppBar()
        binding.toolbar.inflateToolBarLayout(
            R.layout.tb_detail_post_fragment,
            binding.collapsingToolbarLayout
        )
    }


    fun setToolbarVocab() {
        setAppBarHideOnScroll(true)
        showAppBar()
        binding.toolbar.inflateToolBarLayout(
            R.layout.tb_vocab_fragment,
            binding.collapsingToolbarLayout
        )
    }

    fun setToolbarFlashCard() {
        setAppBarHideOnScroll(false)
        showAppBar()
        binding.toolbar.inflateToolBarLayout(
            R.layout.tb_flash_card,
            binding.collapsingToolbarLayout
        )
    }

    fun showAppBar() {
        binding.appBarLayout.setExpanded(true, true)
        binding.bottomAppBar.performShow()
    }

    private fun hideAppBar() {
        binding.appBarLayout.setExpanded(false, true)
        binding.bottomAppBar.performHide()
//        binding.bottomAppBar.performHide(true)
    }

    private fun setAppBarHideOnScroll(isHideAble: Boolean) {
        if (isHideAble) {
            val appBarLayout = binding.appBarLayout
            val params = appBarLayout.getChildAt(0).layoutParams as AppBarLayout.LayoutParams
            params.scrollFlags =
                AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
            appBarLayout.getChildAt(0).layoutParams = params

            val bottomAppBar = binding.bottomAppBar
            bottomAppBar.hideOnScroll = true
        } else {
            val appBarLayout = binding.appBarLayout
            val params = appBarLayout.getChildAt(0).layoutParams as AppBarLayout.LayoutParams
            params.scrollFlags = 0
            appBarLayout.getChildAt(0).layoutParams = params

            val bottomAppBar = binding.bottomAppBar
            bottomAppBar.hideOnScroll = false
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.start_main_enter, R.anim.start_main_exit)
    }

    override fun backBookClickListener() {
        bookNavigation.navigateUp()
    }

    override fun backPostClickListener() {
        forumNavigation.navigateUp()
    }
//    override fun backIconClickListener() {
//        bookNavigation.navigateUp()
//    }
}