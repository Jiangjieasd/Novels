package com.guuidea.inreading.ui

import android.view.View
import android.view.ViewGroup
import com.guuidea.inreading.R
import com.guuidea.inreading.ui.base.BaseActivity
import com.guuidea.inreading.ui.dialog.DoubleBtnDialog
import com.guuidea.inreading.widget.banner.IndicatorBanner
import kotlinx.android.synthetic.main.first_recommend_layout.*

/**
 * @file      FirstRecommendActivity
 * @description    首次推荐书籍页面
 * @author         江 杰
 * @createDate     2020/11/11 16:51
 */

class FirstRecommendActivity : BaseActivity() {

    override fun getContentId(): Int {
        return R.layout.first_recommend_layout
    }

    override fun initClick() {
        super.initClick()
        action_bar.imgClicker = View.OnClickListener { finish() }
        action_bar.rightClicker = View.OnClickListener { nextPage() }
        book__banners.setAdapter(object : IndicatorBanner.IndicatorAdapter() {
            override fun initPage(container: ViewGroup, position: Int): View {
                TODO("Not yet implemented")
            }

            override fun getCount(): Int {
                TODO("Not yet implemented")
            }

        })
        book__banners.setOnPageChange(object : IndicatorBanner.OnPageChange {
            override fun onPageChange(position: Int) {
                refreshDetail(position)
                if (book__banners.haveScrollToLast()) {
                    showDialog()
                }
            }

        })
    }

    private fun nextPage() {
        TODO("Not yet implemented")
    }

    private fun refreshDetail(position: Int) {
        TODO("Not yet implemented")
    }

    private fun showDialog() {
        val dialog = DoubleBtnDialog(this,
                "Tips",
                "Go read more good novels, are you ready?",
                "Yes",
                "Wait",
                object : DoubleBtnDialog.OnDialogClickListener {
                    override fun click(dialog: DoubleBtnDialog) {
                        nextPage()
                    }

                },
                object : DoubleBtnDialog.OnDialogClickListener {
                    override fun click(dialog: DoubleBtnDialog) {
                        dialog.dismiss()
                    }
                }
        )
        dialog.show()
    }
}