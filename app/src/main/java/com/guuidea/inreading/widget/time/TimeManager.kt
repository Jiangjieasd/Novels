package com.guuidea.inreading.widget.time


import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Message


/**
 * @file      TimeManager
 * @description    倒计时管理
 * @author         江 杰
 * @createDate     2020/11/5 17:05
 */
class TimeManager(hour: Int, minute: Int, second: Int, val mListener: OnTimeRunListener) {

    private var mLastTimes: Long = 0L
    private val mTimeout: TimeOut

    companion object {
        private const val MSG_GO = 1
    }

    init {
        mTimeout = TimeOut(hour, minute, second)
        mLastTimes = System.currentTimeMillis()
        Thread(TimeRun()).start()
    }

    fun resetTime(hour: Int, minute: Int, second: Int) {
        mTimeout.resetTime(hour, minute, second)
    }

    private val mHandler: Handler = object : Handler() {
        @SuppressLint("HandlerLeak")
        override fun handleMessage(msg: Message) {
            if (msg.what == MSG_GO && null != mListener) {
                val bundle = msg.data
                mListener.onTimeRun(bundle.getInt("hour"), bundle.getInt("minute"), bundle.getInt("second"))
            }
        }
    }


    private fun timeRun() {
        val curTime = System.currentTimeMillis()
        if (curTime - mLastTimes > 1000) {
            mTimeout.goOneSecond()
            mLastTimes += 1000
        }
    }

    private inner class TimeRun : Runnable {
        override fun run() {
            while (!mTimeout.isFinish) {
                try {
                    Thread.sleep(100)
                    timeRun()
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
    }


    private inner class TimeOut(private var hour: Int,
                                private var minute: Int,
                                private var second: Int) {

        fun resetTime(hour: Int, minute: Int, second: Int) {
            this.hour = hour
            this.minute = minute
            this.second = second
        }

        fun goOneSecond() {
            if (isFinish) {
                return
            }
            second--
            if (second < 0) {
                second += 60
                minute -= 1
            }
            if (minute < 0) {
                minute += 60
                hour -= 1
            }
            val message = Message()
            message.what = MSG_GO
            val bundle = Bundle()
            bundle.putInt("hour", hour)
            bundle.putInt("minute", minute)
            bundle.putInt("second", second)
            message.data = bundle
            mHandler.sendMessage(message)
        }

        val isFinish: Boolean
            get() = hour == 0 && minute == 0 && second == 0

        init {
            resetTime(hour, minute, second)
        }
    }

    interface OnTimeRunListener {
        fun onTimeRun(hour: Int, minute: Int, second: Int)
    }

}