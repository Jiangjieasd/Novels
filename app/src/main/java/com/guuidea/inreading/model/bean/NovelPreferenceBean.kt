package com.guuidea.inreading.model.bean

/**
 * @file      NovelPreferenceBean
 * @description    TODO
 * @author         江 杰
 * @createDate     2020/11/5 15:33
 */
data class NovelPreferenceBean(
        val preference: String,
        val count: String,
        val backgroundRes:Int,
        var checked: Boolean
) {
    override fun toString(): String {
        return "Novel Preference Bean:[name:$preference,count:$count]"
    }
}