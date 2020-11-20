package com.guuidea.inreading.model.bean

import android.os.CountDownTimer

/**
 * @file      MainPageDataBean
 * @description    首页数据模型
 * @author         江 杰
 * @createDate     2020/11/9 17:16
 */

open class MainPageDataBean(
        open val type: MainType
)

data class MainPageRecommendBook(override val type: MainType,
                                 val data: ArrayList<RecommendBook>) : MainPageDataBean(type)

data class MainPageNewbie(override val type: MainType, val countDownTime: String) : MainPageDataBean(type)

data class MainPageLatestRelease(override val type: MainType, val data: ArrayList<RecommendBook>) : MainPageDataBean(type)

data class MainPageMostReviewed(override val type: MainType, val data: ArrayList<RecommendBook>) : MainPageDataBean(type)


enum class MainType {
    RECOMMNED,
    NEWBIE,
    LATESTRELEASE,
    MOSTVIEWED,
    FEEDBACK
}