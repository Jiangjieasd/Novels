package com.guuidea.inreading.ui.activity

import android.view.View
import com.guuidea.inreading.R
import com.guuidea.inreading.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_vip_buy_page.*
import kotlinx.android.synthetic.main.include_vip_detail.*

/**
 * @file      VIPPurchaseActivity
 * @description    会员购买页
 * @author         江 杰
 * @createDate     2020/11/9 14:05
 */
class VIPPurchaseActivity : BaseActivity() {

    private var isShowVipDetail: Boolean = false

    override fun getContentId(): Int {
        return R.layout.activity_vip_buy_page
    }

    override fun initClick() {
        super.initClick()
        vipDetails.setOnClickListener {
            isShowVipDetail = !isShowVipDetail
            details.visibility = if (isShowVipDetail) View.VISIBLE else View.GONE
            divider_line.visibility = if (isShowVipDetail) View.VISIBLE else View.GONE
        }
        payForVip.setOnClickListener {
            payForVIP()
        }
       action_bar.title
    }

    private fun payForVIP() {}
}