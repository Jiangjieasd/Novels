package com.guuidea.inreading.model.bean

/**
 * @file      MainPageDataBean
 * @description    首页数据模型
 * @author         江 杰
 * @createDate     2020/11/9 17:16
 */

data class MainPageDataBean(
        val type: MainType,
        val data: String
)

enum class MainType {
    RECOMMNED,
    NEWBIE,
    LATESTRELEASE,
    MOSTVIEWED,
    FEEDBACK
}