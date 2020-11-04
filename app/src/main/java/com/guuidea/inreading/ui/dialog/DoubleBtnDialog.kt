package com.guuidea.inreading.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.TextView
import com.guuidea.inreading.R
import com.squareup.haha.perflib.RootType

/**
 * @file      DoubleBtnDialog
 * @description    双键弹窗
 * @author         江 杰
 * @createDate     2020/11/4 9:44
 */
class DoubleBtnDialog @JvmOverloads constructor(private val ctx: Context,
                                                private var dialogTitle: String = "Tip",
                                                private var dialogContent: String = "Add this novel to books",
                                                private var dialogLeftText: String = "Cancel",
                                                private var dialogRightText: String = "Add",
                                                private val dialogLeftClicker: OnDialogClickListener,
                                                private val dialogRightClicker: OnDialogClickListener) : Dialog(ctx) {

    private val tvTitle: TextView
    private val tvContent: TextView
    private val btnLeft: TextView
    private val btnRight: TextView

    init {
        val rootView: View = LayoutInflater.from(context).inflate(R.layout.common_dialog, null)
        tvTitle = rootView.findViewById(R.id.tv_title)
        tvContent = rootView.findViewById(R.id.tv_content)
        btnLeft = rootView.findViewById(R.id.btn_left)
        btnRight = rootView.findViewById(R.id.btn_right)
        tvTitle.text = dialogTitle
        tvContent.text = dialogContent
        btnLeft.text = dialogLeftText
        btnRight.text = dialogRightText
        btnLeft.setOnClickListener {
            dialogLeftClicker.click(this)
        }
        btnRight.setOnClickListener {
            dialogRightClicker.click(this)
        }
    }

    override fun show() {
        super.show()
        window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            addFlags(Window.FEATURE_NO_TITLE)
        }
    }

    interface OnDialogClickListener {
        fun click(dialog: DoubleBtnDialog)
    }
}