package com.guuidea.inreading.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.LinearLayout
import android.widget.TextView
import com.guuidea.inreading.R

/**
 * @file      VipRenewDialog
 * @description    VIP 续费弹窗
 * @author         江 杰
 * @createDate     2020/10/28 11:07
 */
class VipRenewDialog constructor(
        ctx: Context,
        private val payClicker: OnClicker,
        private val exitClicker: OnClicker) : Dialog(ctx) {

    private val tvVipDesc: TextView
    private val tvContinuePay: TextView
    private val tvExit: TextView

    init {
        val contentView = LayoutInflater.from(ctx).inflate(R.layout.dialog_renew, null)
        tvVipDesc = contentView.findViewById(R.id.tv_vip_desc)
        tvContinuePay = contentView.findViewById(R.id.tv_continue_pay)
        tvExit = contentView.findViewById(R.id.tv_exit)
        tvContinuePay.setOnClickListener {
            payClicker.click(it)
            dismiss()
        }
        tvExit.setOnClickListener {
            exitClicker.click(it)
            dismiss()
        }
        setCancelable(true)
        setCanceledOnTouchOutside(false)
        setContentView(contentView, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT))
    }

    override fun show() {
        super.show()
        window?.apply {
            setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
            addFlags(Window.FEATURE_NO_TITLE)
        }
    }

    interface OnClicker {
        /**
         * 点击触发
         */
        fun click(view: View)
    }
}