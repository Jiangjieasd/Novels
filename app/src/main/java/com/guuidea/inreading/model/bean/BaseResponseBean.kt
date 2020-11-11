package com.guuidea.inreading.model.bean

/**
 * @file      BaseResponseBean
 * @description    基础model
 * @author         江 杰
 * @createDate     2020/11/11 11:09
 */
data class BaseResponseBean(
        val code: Int,
        val data: Any?,
        val msg: String
)