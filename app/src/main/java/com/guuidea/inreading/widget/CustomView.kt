package com.guuidea.inreading.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import android.view.View

/**
 * @file      CustomView
 * @description    TODO
 * @author         江 杰
 * @createDate     2020/11/4 15:55
 */
class CustomView : View {
    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, atrrs: AttributeSet?, defStyle: Int) : super(context, atrrs, 0)


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val path = Path()
        val paint = Paint()
        paint.color = Color.parseColor("#ffe9e9e9")
        paint.style = Paint.Style.FILL
        canvas?.save()
        canvas?.translate(height / 2F, height / 2F)
        path.addArc(RectF(-height / 2F, -height / 2F, height / 2F, height / 2F), 90F, 180F)
        path.moveTo(0F,-height/2F)
        path.lineTo(width*0.6F,0F)
        path.addArc(RectF(width*0.6F-height/2,-height / 2F,width*0.6F+height/2,height/2F),90F,180F)
        path.moveTo(width*0.6F,height/2F)
        path.lineTo(0F,height/2F)
        path.close()
        canvas?.drawPath(path, paint)
        canvas?.restore()
    }
}