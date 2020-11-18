package com.guuidea.inreading.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.guuidea.inreading.R

/**
 * @file      SingleFunctionItem
 * @description    列表项
 * @author         江 杰
 * @createDate     2020/10/29 11:03
 */
class SingleFunctionItem : FrameLayout {

    private var leftImageRes: Int = 0
    private var centerText: String = ""
    private var rightImageRes: Int = 0
    private val leftImageView by lazy {
        root.findViewById<ImageView>(R.id.img_left)
    }
    private val centerTextView by lazy {
        root.findViewById<TextView>(R.id.tv_center_desc)
    }
    private val rightImageView: ImageView by lazy {
        root.findViewById<ImageView>(R.id.img_right)
    }
    private var itemClicker: OnClickListener? = null

    private val root: View by lazy {
        LayoutInflater.from(context).inflate(R.layout.item_mine_function, this)
    }

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.SingleFunctionItem)
        leftImageRes = typeArray.getResourceId(R.styleable.SingleFunctionItem_func_left, 0)
        centerText = typeArray.getString(R.styleable.SingleFunctionItem_func_center) ?: ""
        rightImageRes = typeArray.getResourceId(R.styleable.SingleFunctionItem_func_right, 0)
        typeArray.recycle()
        bindData()
    }

    private fun bindData() {
        leftImageView.setImageResource(leftImageRes)
        centerTextView.text = centerText
        rightImageView.setImageResource(rightImageRes)
    }

    fun setItemClicker(clicker: OnClickListener) {
        this.itemClicker = clicker
        root.setOnClickListener(itemClicker)
    }

    fun getClicker(): OnClickListener? {
        return itemClicker
    }

    fun setCenterText(str: String) {
        centerTextView.text = str
    }


}