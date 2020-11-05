package com.guuidea.inreading.ui.activity

import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.guuidea.inreading.R
import com.guuidea.inreading.model.bean.NovelPreferenceBean
import com.guuidea.inreading.ui.base.BaseActivity
import com.guuidea.inreading.ui.base.adapter.UniversalBaseAdapter
import com.guuidea.inreading.ui.base.adapter.UniversalViewHolder

/**
 * @file      ChooseNovelPreferenceActivity
 * @description    兴趣标签选择页面
 * @author         江 杰
 * @createDate     2020/11/5 15:16
 */
class ChooseNovelPreferenceActivity : BaseActivity() {

    private val rvClass: RecyclerView by lazy {
        findViewById<RecyclerView>(R.id.rv_class)
    }
    private val tvConfirm: TextView by lazy {
        findViewById<TextView>(R.id.tv_confirm)
    }

    private var confirmCount: Int = 0

    override fun getContentId(): Int {
        return R.layout.activity_choose_novel_preference
    }

    override fun initWidget() {
        super.initWidget()
        rvClass.adapter = object : UniversalBaseAdapter<NovelPreferenceBean>(this, testData()) {
            override fun getItemLayoutId(): Int {
                return R.layout.item_novel_preference
            }

            override fun bindData(holder: UniversalViewHolder, item: NovelPreferenceBean, position: Int) {
                holder.setText(R.id.tv_preference, "${item.preference} \n (${item.count})")
                holder.setImageRes(R.id.img_preference_background, item.backgroundRes)
                holder.itemView.setOnClickListener {
                    if (item.checked) {
                        item.checked = false
                        confirmCount--
                    } else {
                        item.checked = true
                        confirmCount++
                    }
                    refreshCount()
                }
            }
        }
        rvClass.layoutManager = GridLayoutManager(this, 2)
        tvConfirm.setOnClickListener {
            if (0 == confirmCount) return@setOnClickListener
            nextStep()
        }
    }

    private fun refreshCount() {
        tvConfirm.text = "Confirm($confirmCount)"
        tvConfirm.setBackgroundResource(if (confirmCount > 0)
            R.drawable.backg_sol_ff333333_cor_5dp else R.drawable.backg_sol_d0d0d0_cor_5)
    }

    /**
     * 当选中偏好不为0时的逻辑处理
     */
    private fun nextStep() {
        TODO("待实现 ")
    }

    private fun testData(): ArrayList<NovelPreferenceBean> {
        val result: ArrayList<NovelPreferenceBean> = ArrayList()
        for (index in 1..16) {
            val novel: NovelPreferenceBean = NovelPreferenceBean("WuXia",
                    "100+", R.drawable.shelf, false)
            result.add(novel)
        }
        return result
    }

}