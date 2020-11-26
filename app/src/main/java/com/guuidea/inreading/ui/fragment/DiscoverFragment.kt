package com.guuidea.inreading.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.guuidea.inreading.R
import com.guuidea.inreading.model.bean.*
import com.guuidea.inreading.ui.activity.RankingActivity
import com.guuidea.inreading.ui.activity.SearchBookActivity
import com.guuidea.inreading.ui.adapter.MainAdapter
import com.guuidea.inreading.ui.base.BaseFragment
import com.squareup.haha.perflib.Main
import kotlinx.android.synthetic.main.fragment_discover_layout.*
import kotlinx.android.synthetic.main.include_head_search.*
import java.util.concurrent.CountDownLatch

/**
 * @file      DiscoverFragment
 * @description    主页
 * @author         江 杰
 * @createDate     2020/10/26 15:14
 */
class DiscoverFragment : BaseFragment() {

    private val countDownLatch: CountDownLatch = CountDownLatch(3)

    override fun getContentId(): Int {
        return R.layout.fragment_discover_layout
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        loadRecommendBook()
        loadLatestRelease()
        loadMostViewed()
        countDownLatch.await()
        //待上述三个请求完成后进行数据填充操作
        setupAdapter()
    }

    private fun loadRecommendBook() {
        Thread(Runnable {
            Thread.sleep(1000)
            countDownLatch.countDown()
        }).start()
    }

    private fun loadLatestRelease() {
        Thread(Runnable {
            Thread.sleep(1000)
            countDownLatch.countDown()
        }).start()
    }

    private fun loadMostViewed() {
        Thread(Runnable {
            Thread.sleep(1000)
            countDownLatch.countDown()
        }).start()
    }

    private fun setupAdapter() {
        rv_content.layoutManager = LinearLayoutManager(context)
        rv_content.adapter = context?.let { MainAdapter(it, test()) }
    }

    override fun initClick() {
        super.initClick()
        edit_search.setOnClickListener {
            startActivity(Intent(context, SearchBookActivity::class.java))
        }

        img_class.setOnClickListener {
            startActivity(Intent(context,RankingActivity::class.java))
        }
    }

    private fun test(): ArrayList<MainPageDataBean> {
        val result = ArrayList<MainPageDataBean>()
        //推荐书籍待传入
        val one = MainPageRecommendBook(MainType.RECOMMNED, ArrayList())
        //静态数据，无需修改
        val two = MainPageNewbie(MainType.NEWBIE, "")
        val three = MainPageLatestRelease(MainType.LATESTRELEASE, ArrayList())
        val four = MainPageMostReviewed(MainType.MOSTVIEWED, ArrayList())
        //静态数据，无需修改
        val five = MainPageDataBean(MainType.FEEDBACK)
        result.add(one)
        result.add(two)
        result.add(three)
        result.add(four)
        result.add(five)
        return result
    }
}