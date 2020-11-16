package com.guuidea.inreading.ui.base.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * @file      BannerAdapter
 * @description    TODO
 * @author         江 杰
 * @createDate     2020/10/23 9:56
 */
abstract class UniversalBaseAdapter<T>(val ctx: Context?, var dataList: ArrayList<T>)
    : RecyclerView.Adapter<UniversalViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UniversalViewHolder {
        val itemView: View = LayoutInflater.from(ctx).inflate(getItemLayoutId(), parent, false)
        return UniversalViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return if (dataList.isNullOrEmpty()) 0 else dataList.size
    }

    override fun onBindViewHolder(holder: UniversalViewHolder, position: Int) {
        bindData(holder, dataList[position], position)
    }

    /**
     * 修改整个集合的数据
     */
    fun setData(data: ArrayList<T>) {
        dataList = data
        notifyDataSetChanged()
    }

    /**
     * 追加数据
     */
    fun appendData(data: List<T>) {
        val len = dataList.size
        dataList.addAll(data)
        notifyItemRangeChanged(len - 1, data.size)
    }

    /**
     * 返回itemView布局id
     */
    abstract fun getItemLayoutId(): Int

    abstract fun bindData(holder: UniversalViewHolder, item: T, position: Int)

}