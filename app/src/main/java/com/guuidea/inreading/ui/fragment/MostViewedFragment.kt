package com.guuidea.inreading.ui.fragment

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.guuidea.inreading.R
import com.guuidea.inreading.model.remote.RemoteRepository
import com.guuidea.inreading.ui.adapter.BookItemAdapter
import com.guuidea.inreading.ui.base.BaseFragment
import kotlinx.android.synthetic.main.book_list.*

/**
 * @file      MostViewedFragment
 * @description    数据排行页面
 * @author         江 杰
 * @createDate     2020/11/3 13:47
 */
class MostViewedFragment : BaseFragment() {

    private lateinit var bookAdapter: BookItemAdapter

    override fun getContentId(): Int {
        return R.layout.book_list
    }

    override fun initWidget(savedInstanceState: Bundle?) {
        super.initWidget(savedInstanceState)
        setupAdapter()
    }

    private fun setupAdapter() {
        bookAdapter = context?.let { BookItemAdapter(it, ArrayList()) }!!
        rv_book_list.adapter = bookAdapter
        rv_book_list.layoutManager = LinearLayoutManager(context)
    }

    /**
     * 共宿主Activity调用重新刷新数据
     */
    override fun refreshData(type: Int) {
        super.refreshData(type)
        val disposal=RemoteRepository.getInstance()
    }
}