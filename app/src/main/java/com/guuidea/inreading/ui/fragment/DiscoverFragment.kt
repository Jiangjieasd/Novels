package com.guuidea.inreading.ui.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.guuidea.inreading.R
import com.guuidea.inreading.model.bean.MainPageDataBean
import com.guuidea.inreading.model.bean.MainType
import com.guuidea.inreading.ui.base.BaseFragment

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

    class MainAdapter(private val dataList: ArrayList<MainPageDataBean>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = when (viewType) {
            0 -> {
                MainRecommendHolder(LayoutInflater.from(parent.context).inflate(0, parent, false))
            }
            1 -> {
                MainNewBieHolder(LayoutInflater.from(parent.context).inflate(0, parent, false))
            }
            2 -> {
                MainLastReleaseHolder(LayoutInflater.from(parent.context).inflate(0, parent, false))
            }
            3 -> {
                MainMostViewedHolder(LayoutInflater.from(parent.context).inflate(0, parent, false))
            }
            else -> {
                MainFeedbackHolder(LayoutInflater.from(parent.context).inflate(0, parent, false))
            }
        }

        override fun getItemCount(): Int {
            return dataList.size
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            when (holder) {
                is MainRecommendHolder -> {
                    bindRecommend(holder)
                }
                is MainNewBieHolder -> {
                    bindNewBie(holder)
                }
                is MainLastReleaseHolder -> {
                    bindLastRelease(holder)
                }
                is MainMostViewedHolder -> {
                    bindMostViewed(holder)
                }
                is MainFeedbackHolder -> {
                    bindFeedback(holder)
                }
            }
        }

        /**
         * 绑定推荐书籍
         */
        private fun bindRecommend(holder: MainRecommendHolder) {
            TODO("待实现")
        }

        /**
         * 绑定新人福利
         */
        private fun bindNewBie(holder: MainNewBieHolder) {
            TODO("待实现")
        }

        /**
         * 绑定最新发布
         */
        private fun bindLastRelease(holder: MainLastReleaseHolder) {
            TODO("待实现")
        }

        /**
         * 绑定最多浏览
         */
        private fun bindMostViewed(holder: MainMostViewedHolder) {
            TODO("待实现")
        }

        /**
         * 绑定反馈
         */
        private fun bindFeedback(holder: MainFeedbackHolder) {
            TODO("待实现")
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

        inner class MainRecommendHolder(view: View) : RecyclerView.ViewHolder(view)

        inner class MainNewBieHolder(view: View) : RecyclerView.ViewHolder(view)

        inner class MainLastReleaseHolder(view: View) : RecyclerView.ViewHolder(view)

        inner class MainMostViewedHolder(view: View) : RecyclerView.ViewHolder(view)

        inner class MainFeedbackHolder(view: View) : RecyclerView.ViewHolder(view)
    }

}