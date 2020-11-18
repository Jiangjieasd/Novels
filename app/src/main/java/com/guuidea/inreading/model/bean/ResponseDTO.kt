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

/**
 * 书籍购买记录
 */
data class BookPurchaseRecord(
        val code: Int,
        val data: BookPurchaseList,
        val msg: String
)

data class BookPurchaseList(
        val pageNumber: Int,
        val pageSize: Int,
        val data: ArrayList<BookRecord>?,
        val totalCount: Int,
        val totalPage: Int
)

data class BookRecord(
        val id: Int,
        val bookEnId: Int,
        val bookName: String,
        val price: Double,
        val createTime: String
)

/**
 * VIP购买记录
 */
data class VIPPurchaseRecord(
        val code: Int,
        val data: VIPPurchaseList,
        val msg: String
)

data class VIPPurchaseList(
        val pageNumber: Int,
        val pageSize: Int,
        val data: ArrayList<VIPRecord>?,
        val totalCount: Int,
        val totalPage: Int
)

data class VIPRecord(
        val id: Int,
        val userId: String,
        val vipType: Int,
        val price: Double,
        val createTime: String,
        val updateTime: String
)


data class AllVIPResource(
        val code: Int,
        val msg: String,
        val data: ArrayList<VIPResource>
)

data class VIPResource(
        val id: Int,
        val month: Int,
        val originalPrice: Double,
        val discountPrice: Double?,
        val discountType: Int,
        val discountDueTime: String?
)

data class VIPInfo(
        val code: Int,
        val data: VIPInfoBean,
        val msg: String
)

data class VIPInfoBean(
        val id: Int,
        val userId: String,
        val dueTime: String
)

data class UserRecommendBooks(
        val code: Int,
        val data: ArrayList<RecommendBook>,
        val msg: String
)

data class RecommendBook(
        val name: String,
        val bookIconUrl: String,
        val brief: String,
        val views: Int,
        val createTime: String,
        val updateTime: String,
        val id: Int
)

data class SingleBookInfo(
        val code: Int,
        val msg: String,
        val data: RecommendBook
)

/**
 * 章节model
 */
data class ChapterBody(
        val code: Int,
        val msg: String,
        val data: ChapterListBean
)

data class ChapterListBean(
        val pageNumber: Int,
        val pageSize: Int,
        val totalCount: Int,
        val totalPage: Int,
        val data: ArrayList<Chapter>
)

data class Chapter(
        val id: Int,
        val chapterName: String,
        val needPay: Boolean
)

/**
 * 书架列表
 */
data class BookShelfBody(
        val code: Int,
        val msg: String,
        val data: BookShelfList
)

data class BookShelfList(
        val pageNumber: Int,
        val pageSize: Int,
        val totalPage: Int,
        val totalCount: Int,
        val data: ArrayList<BookShelfBean>?
)

data class BookShelfBean(
        val id: Int,
        val bookEnId: Int,
        val bookEnName: String,
        val bookEnIcon: String,
        val readChapter: Int,
        val allChapter: Int,
        val wordIndex: Int
)