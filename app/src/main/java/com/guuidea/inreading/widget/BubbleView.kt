package com.guuidea.inreading.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.guuidea.inreading.R
import kotlin.math.round

/**
 * @file      BubbleView
 * @description    气泡TextView
 * @author         江 杰
 * @createDate     2020/11/9 10:06
 */
class BubbleView : AppCompatTextView {

    private val mPaint: Paint = Paint()
    private val mStrokePaint: Paint = Paint()

    /**
     * 背景颜色
     */
    private var bgColor: Int = 0

    /**
     * 描边颜色
     */
    private var strokeColor: Int = 0

    /**
     * 描边宽度
     */
    private var strokeWidth: Int = 0

    /**
     * view高度
     */
    private var heightView: Int = 0

    /**
     * view宽度
     */
    private var widthView: Int = 0

    /**
     * 矩形高
     */
    private var labelHeight: Int = 0

    /**
     * 圆角半径
     */
    private var mRadius: Int = 0

    /**
     * 三角形高
     */
    private var triangleHeight: Int = 0

    /**
     * 三角形方向
     */
    private var triangleFirection: Int = 0
    private var offsetValue: Int = 0

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, detStyle: Int) : super(context, attrs, detStyle) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        if (null != attrs) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BubbleView)
            bgColor = typedArray.getColor(R.styleable.BubbleView_bubbleColor,
                    0)
            strokeColor = typedArray.getColor(R.styleable.BubbleView_bubbleStrokeColor,
                    0)
            strokeWidth = typedArray.getDimensionPixelOffset(R.styleable.BubbleView_bubbleStrokeWidth,
                    0)
            triangleHeight = typedArray.getDimensionPixelOffset(R.styleable.BubbleView_triangleHeight,
                    30)
            triangleFirection = typedArray.getInt(R.styleable.BubbleView_triangleDirection,
                    0)
            offsetValue = typedArray.getDimensionPixelOffset(R.styleable.BubbleView_offset_value,
                    0)
            typedArray.recycle()
        }
        gravity = Gravity.CENTER
        initPaint()
        labelHeight = getFontHeight() + paddingTop + paddingBottom
        heightView = labelHeight + triangleHeight.times(2) + strokeWidth.times(2)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        widthView = paddingStart + getFontWidth() + paddingEnd + strokeWidth * 2
        setMeasuredDimension(widthView, heightView)
    }

    private fun initPaint() = mPaint.apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        isDither = true
        textSize = this@BubbleView.textSize
    }

    private fun initStrokePaint() = mStrokePaint.apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        isDither = true
    }

    override fun onDraw(canvas: Canvas?) {
        drawView(canvas)
        super.onDraw(canvas)
    }

    private fun drawView(canvas: Canvas?) {
        if (0 != strokeColor && 0 != strokeWidth) {
            initStrokePaint()
            mStrokePaint.color = strokeColor
            mRadius = labelHeight / 2 + strokeWidth
            drawRound(canvas, mStrokePaint, 0)
            drawTriangle(canvas, mStrokePaint, 0)
        }

        if (0 != bgColor) {
            mPaint.color = bgColor
            mRadius = labelHeight / 2
            drawRound(canvas, mPaint, strokeWidth)
            drawTriangle(canvas, mPaint, strokeWidth)
        }
    }

    private fun drawRound(canvas: Canvas?, paint: Paint, stroke: Int) {
        canvas?.drawRoundRect(stroke.toFloat(), (triangleHeight + stroke).toFloat(),
                (widthView - stroke).toFloat(), (heightView - triangleHeight - stroke).toFloat(),
                mRadius.toFloat(), mRadius.toFloat(), paint)
    }

    private fun drawTriangle(canvas: Canvas?, paint: Paint, stroke: Int) {
        val path = Path()
        when (triangleFirection) {
            1 -> {
                //上居中
                path.moveTo((widthView / 2 - triangleHeight + stroke / 2).toFloat(),
                        (triangleHeight + stroke).toFloat())
                path.lineTo((widthView / 2).toFloat(), (stroke + stroke / 2).toFloat())
                path.lineTo((widthView / 2 + triangleHeight - stroke / 2).toFloat(),
                        (triangleHeight + stroke).toFloat())
            }
            2 -> {
                //下居中
                path.moveTo((widthView / 2 - triangleHeight + stroke / 2).toFloat(),
                        (heightView - triangleHeight - stroke).toFloat())
                path.lineTo((widthView / 2).toFloat(), (heightView - stroke - stroke / 2).toFloat())
                path.lineTo((widthView / 2 + triangleHeight - stroke / 2).toFloat(),
                        (heightView - triangleHeight - stroke).toFloat())
            }
            3 -> {
                //上偏左
                path.moveTo((widthView / 2 - triangleHeight + stroke / 2).toFloat() - offsetValue,
                        (triangleHeight + stroke).toFloat())
                path.lineTo((widthView / 2).toFloat() - offsetValue,
                        (stroke + stroke / 2).toFloat())
                path.lineTo((widthView / 2 + triangleHeight - stroke / 2).toFloat() - offsetValue,
                        (triangleHeight + stroke).toFloat())
            }
            4 -> {
                //上偏右
                path.moveTo((widthView / 2 - triangleHeight + stroke / 2).toFloat() + offsetValue,
                        (triangleHeight + stroke).toFloat())
                path.lineTo((widthView / 2).toFloat() + offsetValue,
                        (stroke + stroke / 2).toFloat())
                path.lineTo((widthView / 2 + triangleHeight - stroke / 2).toFloat() + offsetValue,
                        (triangleHeight + stroke).toFloat())
            }
            5 -> {
                //下偏左
                path.moveTo((widthView / 2 - triangleHeight + stroke / 2).toFloat() - offsetValue,
                        (heightView - triangleHeight - stroke).toFloat())
                path.lineTo((widthView / 2).toFloat() - offsetValue,
                        (heightView - stroke - stroke / 2).toFloat())
                path.lineTo((widthView / 2 + triangleHeight - stroke / 2).toFloat() - offsetValue,
                        (heightView - triangleHeight - stroke).toFloat())
            }
            6 -> {
                //下偏右
                path.moveTo((widthView / 2 - triangleHeight + stroke / 2).toFloat() + offsetValue,
                        (heightView - triangleHeight - stroke).toFloat())
                path.lineTo((widthView / 2).toFloat() + offsetValue,
                        (heightView - stroke - stroke / 2).toFloat())
                path.lineTo((widthView / 2 + triangleHeight - stroke / 2).toFloat() + offsetValue,
                        (heightView - triangleHeight - stroke).toFloat())
            }
            else -> return
        }
        canvas!!.drawPath(path, paint)
    }

    private fun getFontHeight(): Int = round(mPaint.fontMetrics.descent - mPaint.fontMetrics.ascent).toInt()

    private fun getFontWidth(): Int = mPaint.measureText(text.toString()).toInt()

    fun setBubbleColor(color: Int) {
        bgColor = ContextCompat.getColor(context, color)
        invalidate()
    }

    fun setStroke(color: Int, width: Int) {
        strokeColor = ContextCompat.getColor(context, color)
        strokeWidth = width
        invalidate()
    }

}