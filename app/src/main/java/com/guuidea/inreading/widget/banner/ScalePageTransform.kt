package com.guuidea.inreading.widget.banner

import android.view.View
import androidx.viewpager.widget.ViewPager

/**
 * @file      ScalePageTransform
 * @description    ViewPager左右切换动画
 * @author         江 杰
 * @createDate     2020/10/27 16:41
 */
class ScalePageTransform : ViewPager.PageTransformer {

    companion object {
        const val MIN_SCALE: Float = 0.65F
        const val MIN_ALPHA: Float = 0.5F
    }

    override fun transformPage(page: View, position: Float) {
        if (position < -1 || position > 1) {
            page.alpha = MIN_ALPHA
            page.scaleX = MIN_SCALE
            page.scaleY = MIN_SCALE
        } else if (position <= 1) { // [-1,1]
            val scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position))
            if (position < 0) {
                val scaleX = 1 + 0.2f * position
                page.scaleX = scaleX
                page.scaleY = scaleX
            } else {
                val scaleX = 1 - 0.2f * position
                page.scaleX = scaleX
                page.scaleY = scaleX
            }
            page.alpha = MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA)
        }

    }

}