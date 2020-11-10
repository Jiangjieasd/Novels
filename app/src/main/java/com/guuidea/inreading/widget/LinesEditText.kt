package com.guuidea.inreading.widget

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.guuidea.inreading.R
import kotlin.math.round


/**
 * @file      LinesEditText
 * @description    意见反馈控件
 * @author         江 杰
 * @createDate     2020/10/28 15:28
 */

class LinesEditText : LinearLayout {

    private var mContext: Context? = null
    private var mInputEt: EditText? = null
    private var mInputTv: TextView? = null
    private var MAX_COUNT = 0
    private var hintText: String? = null
    private var hintTextColor = 0
    private var ignoreCnOrEn = false
    private var contentText: String? = null
    private var contentTextSize = 0
    private var contentTextColor = 0
    private var contentViewHeight = 0f
    private val mTextWatcher: TextWatcher = object : TextWatcher {
        private var editStart = 0
        private var editEnd = 0
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) = Unit
        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) = Unit
        override fun afterTextChanged(editable: Editable) {
            editStart = mInputEt!!.selectionStart
            editEnd = mInputEt!!.selectionEnd

            // 先去掉监听器，否则会出现栈溢出
            mInputEt!!.removeTextChangedListener(this)
            if (ignoreCnOrEn) {
                //当输入字符个数超过限制的大小时，进行截断操作
                while (calculateLengthIgnoreCnOrEn(editable.toString()) > MAX_COUNT) {
                    editable.delete(editStart - 1, editEnd)
                    editStart--
                    editEnd--
                }
            } else {
                // 因为是中英文混合，单个字符而言，calculateLength函数都会返回1
                while (calculateLength(editable.toString()) > MAX_COUNT) { // 当输入字符个数超过限制的大小时，进行截断操作
                    editable.delete(editStart - 1, editEnd)
                    editStart--
                    editEnd--
                }
            }
            mInputEt!!.setSelection(editStart)
            // 恢复监听器
            mInputEt!!.addTextChangedListener(this)
            configCount()
        }
    }
    private var textDesc: String = "Description"
    private var textSubDesc: String = "(Required)"
    private var calculateVisibility: Boolean = true

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs)
    }

    fun init(attrs: AttributeSet?) {
        mContext = context
        val typedArray: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.LinesEditText)
        MAX_COUNT = typedArray.getInteger(R.styleable.LinesEditText_lev_maxCount,
                100)
        ignoreCnOrEn = typedArray.getBoolean(R.styleable.LinesEditText_lev_ignoreCnOrEn,
                true)
        hintText = typedArray.getString(R.styleable.LinesEditText_lev_hintText)
        hintTextColor = typedArray.getColor(R.styleable.LinesEditText_lev_hintTextColor,
                Color.parseColor("#42000000"))
        contentText = typedArray.getString(R.styleable.LinesEditText_lev_contentText)
        contentTextColor = typedArray.getColor(R.styleable.LinesEditText_lev_contentTextColor,
                Color.parseColor("#8A000000"))
        contentTextSize = typedArray.getDimensionPixelSize(R.styleable.LinesEditText_lev_contentTextSize,
                dp2px(context, 14f))
        contentViewHeight = typedArray.getDimensionPixelSize(R.styleable.LinesEditText_lev_contentViewHeight,
                dp2px(context, 140f)).toFloat()
        val desc = typedArray.getString(R.styleable.LinesEditText_lev_desc)
        if (desc != null) {
            textDesc = desc
        }
        val subDesc = typedArray.getString(R.styleable.LinesEditText_lev_decs_sub)
        if (subDesc != null) {
            textSubDesc = subDesc
        }
        calculateVisibility = typedArray.getBoolean(R.styleable.LinesEditText_lev_calculate_visibility, true)
        typedArray.recycle()
        initView()
    }

    private fun initView() {
        val view: View = LayoutInflater.from(mContext).inflate(R.layout.lines_edit_text, this)
        view.findViewById<TextView>(R.id.tv_desc).text = textDesc
        view.findViewById<TextView>(R.id.tv_sub_desc).text = textSubDesc
        mInputEt = view.findViewById(R.id.et_input)
        mInputTv = view.findViewById(R.id.tv_input)
        mInputEt!!.addTextChangedListener(mTextWatcher)
        mInputEt!!.hint = hintText
        mInputEt!!.setHintTextColor(hintTextColor)
        mInputEt!!.setText(contentText)
        mInputEt!!.setTextColor(contentTextColor)
        mInputEt!!.setTextSize(TypedValue.COMPLEX_UNIT_PX, contentTextSize.toFloat())
        mInputEt!!.height = contentViewHeight.toInt()
        /**
         * 配合 mInputTv xml的 android:focusable="true"
         * android:focusableInTouchMode="true"
         * 在iet_input设置完文本后
         * 不给et_input 焦点
         */
        mInputTv!!.requestFocus()
        mInputTv!!.visibility = if (calculateVisibility) View.VISIBLE else View.GONE
        configCount()
        mInputEt!!.setSelection(mInputEt!!.length()) // 将光标移动最后一个字符后面
        /**
         * focus后给背景设置Selected
         */
        mInputEt!!.onFocusChangeListener = OnFocusChangeListener { _, b -> this.setSelected(b) }
    }

    private fun calculateLength(c: CharSequence): Long {
        var len = 0.0
        for (element in c) {
            val tmp = element.toInt()
            if (tmp in 1..126) {
                len += 0.5
            } else {
                len++
            }
        }
        return round(len).toLong()
    }

    private fun calculateLengthIgnoreCnOrEn(c: CharSequence): Int {
        var len = 0
        for (i in c.indices) {
            len++
        }
        return len
    }

    private fun configCount() {
        if (ignoreCnOrEn) {
            val nowCount = calculateLengthIgnoreCnOrEn(mInputEt!!.text.toString())
            mInputTv!!.text = "$nowCount/$MAX_COUNT"
        } else {
            val nowCount = calculateLength(mInputEt!!.text.toString())
            mInputTv!!.text = "$nowCount/$MAX_COUNT"
        }
    }

    fun setContentText(content: String?) {
        contentText = content
        if (mInputEt == null) {
            return
        }
        mInputEt!!.setText(contentText)
    }

    fun getContentText(): String? {
        if (mInputEt != null) {
            contentText = if (mInputEt!!.text == null) "" else mInputEt!!.text.toString()
        }
        return contentText
    }

    fun setHintText(hintText: String?) {
        this.hintText = hintText
        if (mInputEt == null) {
            return
        }
        mInputEt!!.hint = hintText
    }

    fun setContentTextSize(size: Int) {
        if (mInputEt == null) {
            return
        }
        mInputEt!!.setTextSize(TypedValue.COMPLEX_UNIT_PX, size.toFloat())
    }

    fun setContentTextColor(color: Int) {
        if (mInputEt == null) {
            return
        }
        mInputEt!!.setTextColor(color)
    }

    fun setHintColor(color: Int) {
        if (mInputEt == null) {
            return
        }
        mInputEt!!.setHintTextColor(color)
    }

    fun getHintText(): String? {
        if (mInputEt != null) {
            hintText = if (mInputEt!!.hint == null) "" else mInputEt!!.hint.toString()
        }
        return hintText
    }

    fun setMaxCount(max_count: Int) {
        MAX_COUNT = max_count
        configCount()
    }

    fun setIgnoreCnOrEn(ignoreCnOrEn: Boolean) {
        this.ignoreCnOrEn = ignoreCnOrEn
        configCount()
    }

    private fun dp2px(context: Context, dp: Float): Int {
        val scale: Float = context.getResources().getDisplayMetrics().density
        return (dp * scale + 0.5f).toInt()
    }
}