package com.guuidea.inreading.ui.activity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.flexbox.*
import com.google.android.material.tabs.TabLayout
import com.guuidea.inreading.R
import com.guuidea.inreading.ui.adapter.BookClassPagerAdapter
import com.guuidea.inreading.ui.base.BaseActivity
import com.guuidea.inreading.ui.base.BaseFragment
import com.guuidea.inreading.ui.fragment.LastReleaseFragment
import com.guuidea.inreading.ui.fragment.MostViewedFragment
import com.guuidea.inreading.widget.CustomActionbar


/**
 * @file      RankingActivity
 * @description    TODO
 * @author         江 杰
 * @createDate     2020/11/3 10:40
 */
class RankingActivity : BaseActivity() {

    private val customBar by lazy {
        findViewById<CustomActionbar>(R.id.action_bar)
    }
    private val flexBox by lazy {
        findViewById<RecyclerView>(R.id.flex_box)
    }
    private val tabs by lazy {
        findViewById<TabLayout>(R.id.tabs)
    }
    private val bookCounts by lazy {
        findViewById<TextView>(R.id.book_count)
    }
    private val vgBooks by lazy {
        findViewById<ViewPager>(R.id.vg_books)
    }

    private val fragments: ArrayList<BaseFragment> = ArrayList()

    /**
     * 书籍类别
     */
    private val bookClass: Array<String> = arrayOf(
            "Fantasy", "WuXia", "Modern",
            "Sci-fi", "Romance"
    )
    private val titles: Array<String> = arrayOf(
            "Most Viewed", "Last release"
    )

    override fun getContentId(): Int {
        return R.layout.activity_ranking
    }

    override fun initWidget() {
        super.initWidget()
        setupAdapter()
        setupViewPager()
    }

    private fun setupAdapter() {
        val flexLayoutManager = FlexboxLayoutManager(this)
        flexLayoutManager.flexWrap = FlexWrap.WRAP
        flexLayoutManager.flexDirection = FlexDirection.ROW
        flexLayoutManager.alignItems = AlignItems.STRETCH
        flexLayoutManager.justifyContent = JustifyContent.FLEX_START
        flexBox.layoutManager = flexLayoutManager
        flexBox.adapter = BookClassAdapter(bookClass, object : OnItemClick {
            override fun onItemClick(position: Int) {
                onBookClassClickEvent(position)
            }
        })
    }

    private fun setupViewPager() {
        fragments.add(MostViewedFragment())
        fragments.add(LastReleaseFragment())
        vgBooks.adapter = BookClassPagerAdapter(supportFragmentManager, fragments, titles)
        tabs.setupWithViewPager(vgBooks)
    }

    /**
     * 顶部书籍类别item点击相应
     */
    private fun onBookClassClickEvent(positon: Int) {
        fragments.forEach {
            it.refreshData(positon)
        }
    }

    class BookClassAdapter(private val datas: Array<String>,
                           private val itemClick: OnItemClick) : RecyclerView.Adapter<BookClassAdapter.BookClassViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookClassViewHolder {
            return BookClassViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_book_item, parent, false))
        }

        override fun getItemCount(): Int {
            return datas.size
        }

        override fun onBindViewHolder(holder: BookClassViewHolder, position: Int) {
            (holder.itemView as TextView).setText(datas[position])
            holder.itemView.setOnClickListener {
                itemClick.onItemClick(position)
            }
        }

        inner class BookClassViewHolder(view: View) : RecyclerView.ViewHolder(view) {}
    }

    interface OnItemClick {
        fun onItemClick(position: Int): Unit
    }

}