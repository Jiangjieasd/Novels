package com.guuidea.inreading.utils

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.annotation.ColorInt
import okhttp3.internal.Util.getSystemProperty


/**
 * @file      StatusUtil
 * @description    沉浸式状态栏
 * @author         江 杰
 * @createDate     2020/11/23 15:23
 */

object StatusUtil {
    /**
     * Use default color [.defaultColor_21] between 5.0 and 6.0.
     */
    const val USE_DEFAULT_COLOR = -1

    /**
     * Use color [.setUseStatusBarColor] between 5.0 and 6.0.
     */
    const val USE_CUR_COLOR = -2

    /**
     * Default status bar color between 21(5.0) and 23(6.0).
     * If status color is white, you can set the color outermost.
     */
    var defaultColor_21: Int = Color.parseColor("#33000000")

    private const val KEY_EMUI_VERSION_NAME = "ro.build.version.emui"

    /**
     * Setting the status bar color.
     * It must be more than 21(5.0) to be valid.
     *
     * @param color Status color.
     */
    fun setUseStatusBarColor(activity: Activity, @ColorInt color: Int) =
            setUseStatusBarColor(activity, color, USE_CUR_COLOR)

    /**
     * It must be more than 21(5.0) to be valid.
     * Setting the status bar color.Supper between 21 and 23.
     *
     * @param color        Status color.
     * @param surfaceColor Between 21 and 23,if surfaceColor == [.USE_DEFAULT_COLOR],the status color is defaultColor_21,
     * else if surfaceColor == [.USE_CUR_COLOR], the status color is color,
     * else the status color is surfaceColor.
     */
    fun setUseStatusBarColor(activity: Activity, @ColorInt color: Int, surfaceColor: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.window.statusBarColor =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M || surfaceColor == USE_CUR_COLOR) color
                    else if (surfaceColor == USE_DEFAULT_COLOR) defaultColor_21
                    else surfaceColor
        }
    }

    /**
     * Setting the status bar transparently.
     * See [.setUseStatusBarColor].
     */
    fun setTransparentStatusBar(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // after 21(5.0)
            setUseStatusBarColor(activity, Color.TRANSPARENT)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // between 19(4.4) and 21(5.0)
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
    }

    /**
     * Setting up whether or not to invade the status bar & status bar font color
     *
     * @param isTransparent Whether or not to invade the status bar?
     * If true, will invade the status bar,
     * otherwise, fits system windows.
     * @param isBlack       Whether the status bar font is set to black?
     * If true, the status bar font will be black,
     * otherwise, it is white.
     */
    fun setSystemStatus(activity: Activity, isTransparent: Boolean, isBlack: Boolean) {
        var flag = 0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && isTransparent) {
            // after 16(4.1)
            flag = flag or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && isBlack) {
            // after 23(6.0)
            flag = flag or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        activity.window.decorView.systemUiVisibility = flag
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // between 19(4.4) and 21(5.0)
            if (isTransparent) {
                activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            } else {
                activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            }
            val contentView = activity.window.findViewById<View>(Window.ID_ANDROID_CONTENT) as ViewGroup
            val childAt = contentView.getChildAt(0)
            if (childAt != null) childAt.fitsSystemWindows = !isTransparent
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && isEMUI3_x()) {
            if (isTransparent) {
                activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            } else {
                activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            }
            val contentView = activity.window.findViewById<View>(Window.ID_ANDROID_CONTENT) as ViewGroup
            val childAt = contentView.getChildAt(0)
            if (childAt != null) childAt.fitsSystemWindows = !isTransparent
        }
    }

    /**
     * Get the height of the state bar by reflection.
     *
     * @return Status bar height if it is not equal to -1,
     */
    fun getStatusBarHeight(context: Context): Int = getSizeByReflection(context, "status_bar_height")

    /**
     * Get the height of the state bar by reflection.
     *
     * @return Status bar height if it is not equal to -1,
     */
    fun getNavigationBarHeight(context: Context): Int = getSizeByReflection(context, "navigation_bar_height")

    fun getSizeByReflection(context: Context, field: String?): Int {
        var size = -1
        try {
            val clazz = Class.forName("com.android.internal.R\$dimen")
            val ob = clazz.newInstance()
            val height = clazz.getField(field)[ob].toString().toInt()
            size = context.getResources().getDimensionPixelSize(height)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return size
    }

    /**
     * Set bottom navigation bar color
     */
    fun setNavigationBar(activity: Activity, @ColorInt color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.window.navigationBarColor = color
        }
    }

    /**
     * 判断是否为emui3.x版本
     * Is emui 3 x boolean.
     *
     * @return the boolean
     */
    fun isEMUI3_x(): Boolean = isEMUI3_0() || isEMUI3_1()

    /**
     * 判断是否为emui3.1版本
     * Is emui 3 1 boolean.
     *
     * @return the boolean
     */
    fun isEMUI3_1(): Boolean {
        val property: String = getEMUIVersion()!!
        return "EmotionUI 3" == property || property.contains("EmotionUI_3.1")
    }

    /**
     * 判断是否为emui3.0版本
     * Is emui 3 1 boolean.
     *
     * @return the boolean
     */
    fun isEMUI3_0(): Boolean {
        val property: String = getEMUIVersion()!!
        return property.contains("EmotionUI_3.0")
    }

    /**
     * 判断是否为emui
     * Is emui boolean.
     *
     * @return the boolean
     */
    fun isEMUI(): Boolean {
        val property: String = getSystemProperty(KEY_EMUI_VERSION_NAME, "")
        return !TextUtils.isEmpty(property)
    }

    /**
     * 得到emui的版本
     * Gets emui version.
     *
     * @return the emui version
     */
    fun getEMUIVersion(): String? = if (isEMUI()) getSystemProperty(KEY_EMUI_VERSION_NAME, "") else ""
}