package com.guuidea.inreading.utils

import android.content.Context
import com.guuidea.inreading.App
import com.guuidea.inreading.model.local.ReadSettingManager
import com.zqc.opencc.android.lib.ChineseConverter
import com.zqc.opencc.android.lib.ConversionType
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs
/**
 * @file      StringUtilsKt
 * @description    TODO
 * @author         江 杰
 * @createDate     2020/10/20 9:27
 */
class StringUtilsKt private constructor() {

    companion object {
        val TAG: String = StringUtilsKt.javaClass.name
        private const val HOUR_OF_DAYS = 24
        private const val DAY_OF_YESTERDAY = 2
        private const val TIME_UNIT = 60

        fun dateConvert(time: Long, pattern: String): String {
            val date = Date(time)
            val format = SimpleDateFormat(pattern)
            return format.format(date)
        }

        fun dateConvert(source: String, pattern: String): String {
            val format = SimpleDateFormat(pattern)
            val calendar = Calendar.getInstance()
            try {
                val date = format.parse(source)
                val curTime = calendar.timeInMillis
                calendar.time = date
                val difSec = abs((curTime - date.time) / 1000)
                val difMin = difSec / 60
                val difHour = difMin / 60
                val difDate = difHour / 60
                val oldHour = calendar.get(Calendar.HOUR)
                if (oldHour == 0) {
                    return when {
                        0L == difDate -> {
                            "今天"
                        }
                        difDate < DAY_OF_YESTERDAY -> {
                            "昨天"
                        }
                        else -> {
                            val convertFormat = SimpleDateFormat("yyyy-MM-dd")
                            convertFormat.format(date)
                        }
                    }
                }
                return when {
                    difSec < TIME_UNIT -> {
                        difSec.toString() + "秒前"
                    }
                    difMin < TIME_UNIT -> {
                        difMin.toString() + "分钟前"
                    }
                    difHour < HOUR_OF_DAYS -> {
                        difHour.toString() + "小时前"
                    }
                    difDate < DAY_OF_YESTERDAY -> {
                        "昨天"
                    }
                    else -> {
                        val convertFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
                        convertFormat.format(date)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return ""
        }

        fun toFirstCapital(str: String): String =
                str.substring(0, 1).toUpperCase(Locale.ROOT) + str.substring(1)

        fun getString(id: Int, vararg formatArgs: Any) =
                App.getContext().resources.getString(id, formatArgs)

        fun getString(id: Int) = App.getContext().resources.getString(id)

        fun halfToFull(input: String): String {
            val c = input.toCharArray()
            val len = c.size
            for (index in 0..len) {
                if (c[index] == 32.toChar()) {
                    c[index] = 12288.toChar()
                    continue
                }

                if (c[index] > 32.toChar() && c[index] < 127.toChar()) {
                    c[index] = c[index] + 65248
                }
            }
            return c.toString()
        }

        fun fullToHalf(input: String): String {
            val c = input.toCharArray()
            val len = c.size
            for (index in 0..len) {
                if (c[index] == 12288.toChar()) {
                    c[index] = 32.toChar()
                    continue
                }

                if (c[index] > 65280.toChar() && c[index] < 65375.toChar()) {
                    c[index] = c[index] - 65248
                }
            }
            return c.toString()
        }

        fun convertCC(input: String, ctx: Context): String {
            var currentConversionType = ConversionType.S2TWP
            val convertType = SharedPreUtils.getInstance()
                    .getInt(ReadSettingManager.SHARED_READ_CONVERT_TYPE, 0)
            if (input.isEmpty()) {
                return ""
            }

            when (convertType) {
                1 -> currentConversionType = ConversionType.TW2SP
                2 -> currentConversionType = ConversionType.S2HK
                3 -> currentConversionType = ConversionType.S2T
                4 -> currentConversionType = ConversionType.S2TW
                5 -> currentConversionType = ConversionType.S2TWP
                6 -> currentConversionType = ConversionType.T2HK
                7 -> currentConversionType = ConversionType.T2S
                8 -> currentConversionType = ConversionType.T2TW
                9 -> currentConversionType = ConversionType.TW2S
                10 -> currentConversionType = ConversionType.HK2S
                else -> ConversionType.S2TWP
            }

            return if (convertType != 0)
                ChineseConverter.convert(input, currentConversionType, ctx)
            else input
        }
    }

}