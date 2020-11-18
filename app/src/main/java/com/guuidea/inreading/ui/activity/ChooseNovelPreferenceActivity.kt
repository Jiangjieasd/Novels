package com.guuidea.inreading.ui.activity

import android.util.SparseBooleanArray
import android.view.View
import android.widget.ImageView
import androidx.core.util.forEach
import androidx.core.util.remove
import androidx.recyclerview.widget.GridLayoutManager
import com.guuidea.inreading.R
import com.guuidea.inreading.model.bean.NovelPreferenceBean
import com.guuidea.inreading.model.remote.RemoteRepository
import com.guuidea.inreading.ui.base.BaseActivity
import com.guuidea.inreading.ui.base.adapter.UniversalBaseAdapter
import com.guuidea.inreading.ui.base.adapter.UniversalViewHolder
import com.guuidea.inreading.utils.LogUtils
import com.guuidea.inreading.utils.RxUtils
import com.guuidea.inreading.utils.ToastUtils
import kotlinx.android.synthetic.main.activity_choose_novel_preference.*

/**
 * @file      ChooseNovelPreferenceActivity
 * @description    兴趣标签选择页面
 * @author         江 杰
 * @createDate     2020/11/5 15:16
 */
class ChooseNovelPreferenceActivity : BaseActivity() {

    private var confirmCount: Int = 0
    private val backgroundRes: Array<Int> = arrayOf(
            R.drawable.fantasy, R.drawable.xianxia,
            R.drawable.wuxia, R.drawable.science,
            R.drawable.modern, R.drawable.romance,
            R.drawable.mystery
    )
    private val tagRes: Array<Int> = arrayOf(
            1, 2,
            4, 6,
            7, 9,
            8
    )

    private val confirmResult: SparseBooleanArray = SparseBooleanArray()

    override fun getContentId(): Int {
        return R.layout.activity_choose_novel_preference
    }

    override fun initWidget() {
        super.initWidget()
        rv_class.adapter = object : UniversalBaseAdapter<NovelPreferenceBean>(this,
                productData()) {
            override fun getItemLayoutId(): Int {
                return R.layout.item_novel_preference
            }

            override fun bindData(holder: UniversalViewHolder,
                                  item: NovelPreferenceBean,
                                  position: Int) {

                holder.setImageRes(R.id.img_preference_background, item.backgroundRes)
                holder.itemView.setOnClickListener {
                    if (item.checked) {
                        item.checked = false
                        confirmCount--
                        confirmResult.remove(item.tag, true)
                    } else {
                        item.checked = true
                        confirmCount++
                        confirmResult.put(item.tag, true)
                    }
                    refreshCount()
                    holder.getView<ImageView>(R.id.img_check).visibility =
                            if (item.checked) View.VISIBLE else View.INVISIBLE
                }
            }
        }
        rv_class.layoutManager = GridLayoutManager(this, 2)
        tv_confirm.setOnClickListener {
            if (0 == confirmCount) return@setOnClickListener
            nextStep(false)
        }
    }

    override fun initClick() {
        super.initClick()
        action_bar.imgClicker = View.OnClickListener { finish() }
        action_bar.rightClicker = View.OnClickListener { nextStep(true) }
    }

    private fun refreshCount() {
        tv_confirm.text = "Confirm($confirmCount)"
        tv_confirm.setBackgroundResource(if (confirmCount > 0)
            R.drawable.backg_sol_ff333333_cor_5dp else R.drawable.backg_sol_d0d0d0_cor_5)
    }

    /**
     * 当选中偏好不为0时的逻辑处理(根据是否skip来区分用户点击actionbar还是点击confirm按钮)
     */
    private fun nextStep(skip: Boolean) {
        val result = ArrayList<Int>()
        confirmResult.forEach { key, _ ->
            result.add(key)
        }
        val disposable = RemoteRepository.getInstance().savePreference(result)
                .compose(RxUtils::toSimpleSingle)
                .subscribe(
                        {
                            if (0 == it.code) {
                                ToastUtils.show("Success!")
                            }
                        },
                        {
                            LogUtils.e(it)
                        })
        addDisposable(disposable)
    }

    private fun productData(): ArrayList<NovelPreferenceBean> {
        val result: ArrayList<NovelPreferenceBean> = ArrayList()
        for (index in backgroundRes.indices) {
            val novel = NovelPreferenceBean(backgroundRes[index], tagRes[index], false)
            result.add(novel)
        }
        return result
    }

}