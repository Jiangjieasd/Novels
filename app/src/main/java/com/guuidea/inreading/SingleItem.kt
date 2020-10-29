package com.guuidea.inreading

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView

class SingleItem : FrameLayout {

    private var content: String? = null
    private var subContent: String? = null
    private var img: Drawable? = null
    var clicker: OnClickListener? = null

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        // Load attributes
        val a = context.obtainStyledAttributes(
                attrs, R.styleable.SingleItem, defStyle, 0)
        content = a.getString(R.styleable.SingleItem_content_str)
        subContent = a.getString(R.styleable.SingleItem_sub_content_str)
        img = a.getDrawable(R.styleable.SingleItem_img)
        a.recycle()
        val view: View = LayoutInflater.from(context).inflate(R.layout.sample_single_item, null)
        view.findViewById<TextView>(R.id.content).text = content
        view.findViewById<TextView>(R.id.sub_content).text = subContent
        view.findViewById<ImageView>(R.id.img).setImageDrawable(img)
        view.setOnClickListener {
            clicker?.onClick(it)
        }
        addView(view)
    }

}