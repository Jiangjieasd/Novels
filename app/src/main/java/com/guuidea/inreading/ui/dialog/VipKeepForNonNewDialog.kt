package com.guuidea.inreading.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.LinearLayout
import android.widget.TextView
import com.guuidea.inreading.R

/**
 * @file      VipKeepForNonNewDialog
 * @description    非新人挽留弹窗
 * @author         江 杰
 * @createDate     2020/11/10 15:08
 */
class VipKeepForNonNewDialog constructor(val ctx: Context,
                                         val payNowClicker: View.OnClickListener,
                                         val cancelClicker: View.OnClickListener) : Dialog(ctx) {

    private val tvContinuePay: TextView
    private val tvExit: TextView

    init {
        val contentView = LayoutInflater.from(ctx).inflate(R.layout.dialog_non_new_keep, null)
        tvContinuePay = contentView.findViewById(R.id.tv_continue_pay)
        tvExit = contentView.findViewById(R.id.tv_exit)
        tvContinuePay.setOnClickListener(payNowClicker)
        tvExit.setOnClickListener(cancelClicker)
        setCancelable(true)
        setCanceledOnTouchOutside(false)
        setContentView(contentView, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT))
    }

    override fun show() {
        super.show()
        window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            addFlags(Window.FEATURE_NO_TITLE)
        }
    }

}