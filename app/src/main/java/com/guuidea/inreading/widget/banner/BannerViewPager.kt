package com.guuidea.inreading.widget.banner

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

/**
 * @file      BannerViewPager
 * @description    TODO
 * @author         江 杰
 * @createDate     2020/11/10 10:35
 */
class BannerViewPager : ViewPager {

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return false
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return false
    }

}