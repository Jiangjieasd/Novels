package com.guuidea.inreading.model.bean

/**
 * @file      LoginDTO
 * @description    请求模型集中管理类
 * @author         江 杰
 * @createDate     2020/11/11 11:18
 */
data class LoginDTO(
        val email: String,
        val password: String
)

data class Feedback(
        val feedback: String
)

data class UserPreferenceDto(
        val tagEnIdList: List<Int>
)

data class UserBookDto(
        val bookEnId: Int
)

data class UserPurchaseDto(
        val pageNum: Int,
        val pageSize: Int
)

data class ChapterDto(
        val bookEnId: String,
        val pageNum: Int,
        val pageSize: Int
)

data class BookDto(
        val bookName: String,
        val order: String,
        val pageNum: Int,
        val pageSize: Int
)

/**
 * 根据标签进行搜索model
 */
data class BookTagDto(
        val order: String,
        val pageNum: Int,
        val pageSize: Int,
        val tagId: Int
)

data class UserBuyVipDto(
        val onSaleId: String,
        val vipId: Int
)

data class DeleteUserBookListDto(
        val listId: Int
)

data class BookContentRequestDto(
        val bookEnId: String,
        val chapterEnId: String
)
