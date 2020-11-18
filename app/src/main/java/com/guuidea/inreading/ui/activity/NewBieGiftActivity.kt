package com.guuidea.inreading.ui.activity

import android.view.View
import com.guuidea.inreading.R
import com.guuidea.inreading.model.bean.BaseResponseBean
import com.guuidea.inreading.model.remote.RemoteRepository
import com.guuidea.inreading.ui.base.BaseActivity
import com.guuidea.inreading.utils.LogUtils
import com.guuidea.inreading.utils.RxUtils
import com.guuidea.inreading.utils.ToastUtils
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_newbie_gift.*
import kotlinx.android.synthetic.main.include_vip_detail.*

/**
 * @file      NewBieGiftActivity
 * @description    新人福利页面
 * @author         江 杰
 * @createDate     2020/11/6 10:10
 */
class NewBieGiftActivity : BaseActivity() {

    private var isShowDetail: Boolean = false

    override fun getContentId(): Int {
        return R.layout.activity_newbie_gift
    }

    override fun initClick() {
        super.initClick()
        vipDetail.setOnClickListener {
            isShowDetail = !isShowDetail
            details.visibility = if (isShowDetail) View.VISIBLE else View.GONE
            divider_line.visibility = if (isShowDetail) View.VISIBLE else View.GONE
        }
        tvPayForVip.setOnClickListener {
            payForVIP()
        }
    }

    private fun payForVIP() {
        val buyVipDisposable = RemoteRepository.getInstance().buyVIP("", 0)
                .compose(RxUtils::toSimpleSingle)
                .subscribe({
                    if (0 == it.code) {
                        ToastUtils.show("Success")
                    }
                },
                        { t -> LogUtils.e(t) })

    }
}