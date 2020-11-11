package com.guuidea.inreading.model.bean

/**
 * @file      ResponseDTO
 * @description    响应model管理类
 * @author         江 杰
 * @createDate     2020/11/11 14:44
 */


/**
 * 用户购买记录
 */
data class PurchaseListDto(
        val code: Int,
        val data: PurchaseList,
        val msg: String
)

data class PurchaseList(
        val pageNumber: Int,
        val pageSize: Int,
        val data: List<Any>,
        val totalCount: Int,
        val totalPage: Int
)
