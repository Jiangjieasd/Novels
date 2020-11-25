package com.guuidea.inreading.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.View
import com.guuidea.inreading.R
import com.guuidea.inreading.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_agreement_policy.*

/**
 * @file      AgreementAndPolicyActivity
 * @description    隐私策略、服务协议
 * @author         江 杰
 * @createDate     2020/11/3 9:54
 */
class AgreementAndPolicyActivity : BaseActivity() {

    private var type: Int? = 0

    override fun getContentId(): Int {
        return R.layout.activity_agreement_policy
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        type = intent.extras?.getInt(TYPE) ?: 0
    }

    override fun initWidget() {
        super.initWidget()
        when (type) {
            0 -> {
                action_bar.title = "Terms of use"
                tvTips.text = Html.fromHtml("<b>Tips:</b>Single novel costs <b>\$6 </b>in other Reading apps")
            }
            1 -> {
                action_bar.title = "Privacy"
            }
        }
        action_bar.setOnClickListener { finish() }
    }


    companion object {
        private const val TYPE: String = "TYPE"

        /**
         * 供外部调起
         */
        fun startAgreementOrPrivacy(context: Context, type: Int) {
            val startIntent = Intent(context, AgreementAndPolicyActivity::class.java)
            startIntent.putExtra(TYPE, type)
            context.startActivity(startIntent)
        }
    }
}