package com.guuidea.inreading.widget.time


import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.guuidea.inreading.R
import java.text.DecimalFormat


/**
 * @file      TimeViewComm
 * @description    倒计时
 * @author         江 杰
 * @createDate     2020/11/5 16:51
 */
class TimeViewComm : LinearLayout {

    private lateinit var mHours: TextView
    private lateinit var mMinutes: TextView
    private lateinit var mSeconds: TextView
    private var mTextColor: Int = Color.WHITE
    private var mBackgroundColor = Color.BLACK
    private var mSpaceColor = Color.BLACK
    private var mTextSize = 30
    private var mRadius = 5
    private var mPaddingHorizontal = 4
    private var mPaddingVertical = 0
    private val df: DecimalFormat = DecimalFormat("00")
    private var mTimeoutManager: TimeManager? = null
    private var mListener: OnTimeoutListener? = null
    private val mTimeoutPoints: ArrayList<TimePoint> = ArrayList()


    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        orientation = HORIZONTAL

        val dm = context.resources.displayMetrics
        mTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, mTextSize.toFloat(), dm).toInt()
        mRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mRadius.toFloat(), dm).toInt()
        mPaddingHorizontal = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mPaddingHorizontal.toFloat(), dm).toInt()
        mPaddingVertical = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mPaddingVertical.toFloat(), dm).toInt()

        val array = context.obtainStyledAttributes(attrs, R.styleable.TimeViewComm)
        mTextColor = array.getColor(R.styleable.TimeViewComm_tvc_textColor, mTextColor)
        mBackgroundColor = array.getColor(R.styleable.TimeViewComm_tvc_backgroundColor, mBackgroundColor)
        mSpaceColor = array.getColor(R.styleable.TimeViewComm_tvc_spaceColor, mSpaceColor)
        mTextSize = array.getDimensionPixelSize(R.styleable.TimeViewComm_tvc_textSize, mTextSize)
        mRadius = array.getDimensionPixelSize(R.styleable.TimeViewComm_tvc_radius, mRadius)
        mPaddingHorizontal = array.getDimensionPixelSize(R.styleable.TimeViewComm_tvc_textPaddingHorizantal, mPaddingHorizontal)
        mPaddingVertical = array.getDimensionPixelSize(R.styleable.TimeViewComm_tvc_textPaddingVertical, mPaddingVertical)
        array.recycle()

        val layoutParams = LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutParams.gravity = Gravity.CENTER
        val drawable = GradientDrawable()
        drawable.setColor(mBackgroundColor)
        drawable.cornerRadius = mRadius.toFloat()

        mHours = TextView(context)
        mHours.layoutParams = layoutParams
        mHours.setTextColor(mTextColor)
        mHours.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize.toFloat())
        mHours.text = context.resources.getString(R.string.init_value)
        mHours.setPadding(mPaddingHorizontal, mPaddingVertical, mPaddingHorizontal, mPaddingVertical)
        mHours.background = drawable

        mMinutes = TextView(context)
        mMinutes.layoutParams = layoutParams
        mMinutes.setTextColor(mTextColor)
        mMinutes.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize.toFloat())
        mMinutes.text = context.resources.getString(R.string.init_value)
        mMinutes.background = drawable
        mMinutes.setPadding(mPaddingHorizontal, mPaddingVertical, mPaddingHorizontal, mPaddingVertical)

        mSeconds = TextView(context)
        mSeconds.layoutParams = layoutParams
        mSeconds.setTextColor(mTextColor)
        mSeconds.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize.toFloat())
        mSeconds.text = context.resources.getString(R.string.init_value)
        mSeconds.background = drawable
        mSeconds.setPadding(mPaddingHorizontal, mPaddingVertical, mPaddingHorizontal, mPaddingVertical)

        val spaceOne = TextView(context)
        spaceOne.layoutParams = layoutParams
        spaceOne.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize.toFloat())
        spaceOne.setTextColor(mSpaceColor)
        spaceOne.text = "h"

        val spaceTwo = TextView(context)
        spaceTwo.layoutParams = layoutParams
        spaceTwo.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize.toFloat())
        spaceTwo.setTextColor(mSpaceColor)
        spaceTwo.text = "m"

        val spaceThree = TextView(context)
        spaceTwo.layoutParams = layoutParams
        spaceTwo.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize.toFloat())
        spaceTwo.setTextColor(mSpaceColor)
        spaceTwo.text = "s"

        addView(mHours)
        addView(spaceOne)
        addView(mMinutes)
        addView(spaceTwo)
        addView(mSeconds)
        addView(spaceThree)

        startTime(11, 12, 13)
    }

    fun startTime(hour: Int, minutes: Int, second: Int) {
        if (null == mTimeoutManager) {
            mTimeoutManager = TimeManager(hour, minutes, second, object : TimeManager.OnTimeRunListener {
                override fun onTimeRun(hour: Int, minute: Int, second: Int) {
                    setTime(df.format(hour.toLong()), df.format(minute.toLong()), df.format(second.toLong()))
                }
            })
        } else {
            mTimeoutManager!!.resetTime(hour, minutes, second)
        }
    }


    protected fun setTime(hour: String?, minute: String?, second: String?) {
        mHours.text = hour
        mMinutes.text = minute
        mSeconds.text = second
        if (null != mListener) {
            checkTimeout()
            checkTimeoutPoint()
        }
    }

    private fun checkTimeoutPoint() {
        val hour = mHours.text.toString()
        val minute = mMinutes.text.toString()
        val second = mSeconds.text.toString()
        for (timePoint in mTimeoutPoints) {
            if (timePoint.isEqual(hour, minute, second)) {
                mListener?.onTimePoint(hour, minute, second)
                mTimeoutPoints.remove(timePoint)
                break
            }
        }
    }

    private fun checkTimeout() {
        if (mHours.text == "00" && mMinutes.text == "00" && mSeconds.text == "00") {
            mListener?.onTimeout()
        }
    }

    fun setOnTimeoutListener(listener: OnTimeoutListener) {
        mListener = listener
    }

    fun addTimeoutPoint(hour: Int, minute: Int, second: Int) {
        val timePoint = TimePoint(
                df.format(hour.toLong()),
                df.format(minute.toLong()),
                df.format(second.toLong()))
        mTimeoutPoints.add(timePoint)
    }


    private data class TimePoint(
            val hour: String,
            val minute: String,
            val second: String
    ) {
        fun isEqual(hour: String, minute: String, second: String): Boolean =
                this.hour == hour && this.minute == minute && this.second == second
    }

    interface OnTimeoutListener {
        fun onTimePoint(hour: String, minute: String, second: String)
        fun onTimeout()
    }
}