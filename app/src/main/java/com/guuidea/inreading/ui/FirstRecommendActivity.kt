package com.guuidea.inreading.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.guuidea.inreading.R
import com.guuidea.inreading.model.bean.RecommendBook
import com.guuidea.inreading.model.bean.UserRecommendBooks
import com.guuidea.inreading.model.remote.RemoteRepository
import com.guuidea.inreading.ui.base.BaseActivity
import com.guuidea.inreading.ui.dialog.DoubleBtnDialog
import com.guuidea.inreading.utils.LogUtils
import com.guuidea.inreading.utils.RxUtils
import com.guuidea.inreading.widget.banner.IndicatorBanner
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.first_recommend_layout.*
import kotlinx.android.synthetic.main.include_book_info_layout.*

/**
 * @file      FirstRecommendActivity
 * @description    首次推荐书籍页面
 * @author         江 杰
 * @createDate     2020/11/11 16:51
 */

class FirstRecommendActivity : BaseActivity() {

    private val data: ArrayList<RecommendBook> = ArrayList()
    private lateinit var adapter: IndicatorBanner.IndicatorAdapter

    override fun getContentId(): Int {
        return R.layout.first_recommend_layout
    }

    override fun processLogic() {
        super.processLogic()
        loadData()
    }

    private fun loadData() {
        val disposable = RemoteRepository.getInstance().fetchRecommendBookInReading()
                .compose(RxUtils::toSimpleSingle)
                .subscribe(
                        {
                            if (0 == it.code) {
                                data.addAll(it.data)
                                refreshDetail(0)
                                adapter.notifyDataSetChanged()
                            }
                        },
                        { t -> LogUtils.e(t) })
        addDisposable(disposable)
    }

    override fun initClick() {
        super.initClick()
        action_bar.imgClicker = View.OnClickListener { finish() }
        action_bar.rightClicker = View.OnClickListener { nextPage() }
        adapter = object : IndicatorBanner.IndicatorAdapter() {
            override fun initPage(container: ViewGroup, position: Int): View {
                val page = LayoutInflater.from(container.context)
                        .inflate(R.layout.item_book_recommend, container, false)
                val imgBookCover: ImageView = page.findViewById(R.id.img_book_cover)
                val color = arrayOf(R.color.COLOR_666666, R.color.color_252525, android.R.color.holo_red_dark)
                imgBookCover.setBackgroundColor(color[position])
                if (data.isNotEmpty()) {
                    Glide.with(container.context)
                            .load(data[position].bookIconUrl)
                            .into(imgBookCover)
                }
                return page
            }

            override fun getCount(): Int {
                return 3
            }

        }
        book__banners.setAdapter(adapter)
        book__banners.setOnPageChange(object : IndicatorBanner.OnPageChange {
            override fun onPageChange(position: Int) {
                refreshDetail(position)
                if (book__banners.haveScrollToLast()) {
//                    showDialog()
                }
            }

        })
    }

    private fun nextPage() {
        TODO("Not yet implemented")
    }

    private fun refreshDetail(position: Int) {
        if (data.isNotEmpty()) {
            tvBookName.text = data[position].name
            tvBookView.text = "${data[position].views} viewed"
            tvBookIntroduction.text = data[position].brief
        }
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