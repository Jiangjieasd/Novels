package com.guuidea.inreading.model.bean

import androidx.annotation.IntDef
import java.lang.annotation.RetentionPolicy

/**
 * @file      BookOperateDescBean
 * @description    TODO
 * @author         江 杰
 * @createDate     2020/10/27 11:21
 */
data class BookOperateDescBean(
        val operateDesc: String,//描述信息
        val operateImgId: Int,//视图新资源id
        val type: Int//类型
) {
    companion object {
        const val VIEW_DETAIL = 1
        const val SHARE_NOVEL = 2
        const val REMOVE_NOVEL = 3
    }

}
