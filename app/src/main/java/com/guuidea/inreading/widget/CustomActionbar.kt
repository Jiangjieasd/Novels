package com.guuidea.inreading.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.guuidea.inreading.R

class CustomActionbar : FrameLayout {

    var title: String? = ""
        set(value) {
            field = value
            tvTitle?.text = field
        }
    private var tvTitle: TextView? = null
    private var imgBack: ImageView? = null
    var imgClicker: OnClickListener? = null

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) :
            super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        initAttrs(attrs)
        initWidget()
        bindEvent()
    }

    private fun initAttrs(attrs: AttributeSet?) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.CustomActionbar)
        title = if (!a.getString(R.styleable.CustomActionbar_titile).isNullOrEmpty())
            a.getString(R.styleable.CustomActionbar_titile) else ""
        a.recycle()
    }

    private fun initWidget() {
        val view: View =
                LayoutInflater.from(context).inflate(R.layout.custom_action_bar, null)
        tvTitle = view.findViewById(R.id.tv_center_title)
        imgBack = view.findViewById(R.id.img_back)
        imgBack?.setOnClickListener {
            imgClicker?.onClick(it)
        }
        addView(view)
    }

    private fun bindEvent() {
        tvTitle?.text = title
    }

}