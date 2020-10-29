package com.guuidea.inreading.ui.base.adapter

import android.util.SparseArray
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView

/**
 * @file      BannerBaseViewHolder
 * @description    TODO
 * @author         江 杰
 * @createDate     2020/10/23 10:23
 */
class UniversalViewHolder(val itemV: View) : RecyclerView.ViewHolder(itemV) {

    private val views: SparseArray<View> = SparseArray()

    fun setText(@IdRes viewId: Int, text: String) {
        getView<TextView>(viewId).text = text
    }

    fun setTextColor(@IdRes viewId: Int, color: Int) {
        getView<TextView>(viewId).setTextColor(color)
    }

    fun setTextSize(@IdRes viewId: Int, textSize: Float) {
        getView<TextView>(viewId).setTextSize(textSize)
    }

    fun setImageRes(@IdRes viewId: Int, @DrawableRes imgSrc: Int) {
        getView<ImageView>(viewId).setImageResource(imgSrc)
    }

    /**
     * 将该方法对外可见，供外部自行设置控件属性，进行数据绑定
     */
    fun <T : View> getView(@IdRes viewId: Int): T {
        if (views.get(viewId) == null) {
            val view = itemV.findViewById<T>(viewId)
            views.put(viewId, view)
            return view
        }
        return views.get(viewId) as T
    }
}