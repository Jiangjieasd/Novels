package com.guuidea.inreading.utils

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager

/**
 * @file      StatusBarCompatKt
 * @description    TODO
 * @author         江 杰
 * @createDate     2020/10/20 10:11
 */
class StatusBarCompatKt private constructor() {

    companion object {
        private const val INVALID_VAL = -1
        private val COLOR_DEFAULT = Color.parseColor("#20000000")


        fun compat(activity: Activity, statusColor: Int) {
            when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> {
                    val window = activity.window
                    //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
                    //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
                    //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    //设置状态栏颜色
                    //设置状态栏颜色
                    window.statusBarColor = statusColor
                }
                else -> {
                    var color = COLOR_DEFAULT
                    val contentView = activity.findViewById<View>(android.R.id.content) as ViewGroup
                    if (statusColor != INVALID_VAL) {
                        color = statusColor
                    }
                    var statusBarView = activity.findViewById<View>(com.guuidea.inreading.R.id.status_bar)
                    if (statusBarView == null) {
                        statusBarView = View(activity)
                        statusBarView.id = com.guuidea.inreading.R.id.status_bar

                        val lp = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                StatusBarCompat.getStatusBarHeight(activity))
                        contentView.addView(statusBarView, lp)
                    }
                    statusBarView.setBackgroundColor(color)
                }
            }
        }

        fun compat(activity: Activity) = compat(activity, INVALID_VAL)

        fun getStatusHeight(ctx: Context): Int {
            val resourceId = ctx.resources.getIdentifier("status_bar_height", "dimen", "android")
            return if (0 < resourceId) ctx.resources.getDimensionPixelOffset(resourceId) else 0
        }
    }
}