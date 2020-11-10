package com.guuidea.inreading.ui.adapter

import android.content.Context
import android.content.Intent
import android.media.Image
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.guuidea.inreading.R
import com.guuidea.inreading.model.bean.MainPageDataBean
import com.guuidea.inreading.model.bean.MainType
import com.guuidea.inreading.ui.activity.VIPPurchaseActivity
import com.guuidea.inreading.widget.banner.BookBanner
import com.squareup.leakcanary.LeakTraceElement

/**
 * @file      MainAdapter
 * @description    首页Adapter
 * @author         江 杰
 * @createDate     2020/11/10 10:57
 */
class MainAdapter(val ctx: Context, private val dataList: ArrayList<MainPageDataBean>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            RecyclerView.ViewHolder = when (viewType) {
        0 -> {
            MainRecommendHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_main_recommend, parent, false))
        }
        1 -> {
            MainNewBieHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_main_newbie, parent, false))
        }
        2 -> {
            MainLastReleaseHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_main_latest_release, parent, false))
        }
        3 -> {
            MainMostViewedHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_main_most_viewed, parent, false))
        }
        else -> {
            MainFeedbackHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_main_feedback, parent, false))
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MainRecommendHolder -> {
                bindRecommend(holder, position)
            }
            is MainNewBieHolder -> {
                bindNewBie(holder, position)
            }
            is MainLastReleaseHolder -> {
                bindLastRelease(holder, position)
            }
            is MainMostViewedHolder -> {
                bindMostViewed(holder, position)
            }
            is MainFeedbackHolder -> {
                bindFeedback(holder, position)
            }
        }
    }

    override fun getItemViewType(position: Int): Int = when (dataList[position].type) {
        MainType.RECOMMNED -> {
            0
        }
        MainType.NEWBIE -> {
            1
        }
        MainType.LATESTRELEASE -> {
            2
        }
        MainType.MOSTVIEWED -> {
            3
        }
        MainType.FEEDBACK -> {
            4
        }
    }

    /**
     * 绑定推荐书籍
     */
    private fun bindRecommend(holder: MainRecommendHolder, position: Int) {
        holder.banner.setAdapter(object : PagerAdapter() {
            override fun isViewFromObject(view: View, `object`: Any): Boolean {
                return view == `object`
            }

            override fun getCount(): Int {
                TODO("Not yet implemented")
            }

            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                return super.instantiateItem(container, position)
            }

            override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
                container.removeView(`object` as View)
            }

        })
    }

    /**
     * 绑定新人福利
     */
    private fun bindNewBie(holder: MainNewBieHolder, position: Int) {
        holder.beVIP.setOnClickListener {
            ctx.startActivity(Intent(ctx, VIPPurchaseActivity::class.java))
        }
    }

    /**
     * 绑定最新发布
     */
    private fun bindLastRelease(holder: MainLastReleaseHolder, position: Int) {
        holder.tvLatestRelease.setOnClickListener {

        }
    }

    /**
     * 绑定最多浏览
     */
    private fun bindMostViewed(holder: MainMostViewedHolder, position: Int) {
        holder.tvMore.setOnClickListener {

        }
    }

    /**
     * 绑定反馈
     */
    private fun bindFeedback(holder: MainFeedbackHolder, position: Int) {
        holder.imgClose.setOnClickListener {
            holder.edFeedback.setText("")
            holder.containerOp.visibility = View.GONE
        }
        holder.imgSubmit.setOnClickListener {
            holder.edFeedback.setText("")
            holder.containerOp.visibility = View.GONE
        }
        holder.edFeedback.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                holder.containerOp.visibility =
                        if (holder.edFeedback.editableText.length > 1) View.VISIBLE
                        else View.GONE

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                TODO("Not yet implemented")
            }

        })
    }

    class MainRecommendHolder(view: View) : RecyclerView.ViewHolder(view) {
        val banner: BookBanner<String> = view.findViewById(R.id.banner)
        val tvBookName: TextView = view.findViewById(R.id.tv_book_name)
    }

    class MainNewBieHolder(view: View) : RecyclerView.ViewHolder(view) {
        val beVIP: TextView = view.findViewById(R.id.beVip)
    }

    class MainLastReleaseHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvLatestRelease: TextView = view.findViewById(R.id.tv_more_last_release)
        val latestReleaseBooks: RecyclerView = view.findViewById(R.id.latest_release_books)
    }

    class MainMostViewedHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvMore: TextView = view.findViewById(R.id.tv_more)
        val mostViewedBooks = view.findViewById<RecyclerView>(R.id.most_viewed_books)
    }

    class MainFeedbackHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgClose = view.findViewById<ImageView>(R.id.img_close)
        val imgSubmit = view.findViewById<ImageView>(R.id.img_submit)
        val beVIP = view.findViewById<TextView>(R.id.beVIP)
        val edFeedback = view.findViewById<EditText>(R.id.ed_feedback)
        val containerOp = view.findViewById<LinearLayout>(R.id.container_op)
    }
}