package com.guuidea.inreading.ui.fragment

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.guuidea.inreading.R
import com.guuidea.inreading.model.bean.MainPageDataBean
import com.guuidea.inreading.model.bean.MainType
import com.guuidea.inreading.ui.adapter.MainAdapter
import com.guuidea.inreading.ui.base.BaseFragment
import com.squareup.haha.perflib.Main
import kotlinx.android.synthetic.main.fragment_discover_layout.*

/**
 * @file      DiscoverFragment
 * @description    主页
 * @author         江 杰
 * @createDate     2020/10/26 15:14
 */
class DiscoverFragment : BaseFragment() {

    override fun getContentId(): Int {
        return R.layout.fragment_discover_layout
    }

    override fun initWidget(savedInstanceState: Bundle?) {
        super.initWidget(savedInstanceState)
        setupAdapter()
    }

    private fun setupAdapter() {
        rv_content.layoutManager = LinearLayoutManager(context)
        rv_content.adapter = context?.let { MainAdapter(it, test()) }
    }

    private fun test(): ArrayList<MainPageDataBean> {
        val result = ArrayList<MainPageDataBean>()
        val one: MainPageDataBean = MainPageDataBean(MainType.RECOMMNED, "")
        val two: MainPageDataBean = MainPageDataBean(MainType.NEWBIE, "")
        val three: MainPageDataBean = MainPageDataBean(MainType.LATESTRELEASE, "")
        val four: MainPageDataBean = MainPageDataBean(MainType.MOSTVIEWED, "")
        val five: MainPageDataBean = MainPageDataBean(MainType.FEEDBACK, "")
        result.add(one)
        result.add(two)
        result.add(three)
        result.add(four)
        result.add(five)
        return result
    }
}