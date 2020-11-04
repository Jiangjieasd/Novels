package com.guuidea.inreading.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import java.lang.RuntimeException

/**
 * @file      BookClassAdapter
 * @description    TODO
 * @author         江 杰
 * @createDate     2020/11/3 11:48
 */

class BookClassPagerAdapter(val fragmentManager: FragmentManager,
                            val fragments: ArrayList<Fragment>, val titles: Array<String>)
    : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    init {
        require(fragments.size == titles.size) {
            throw RuntimeException("Parameters aren't right")
        }
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }

}