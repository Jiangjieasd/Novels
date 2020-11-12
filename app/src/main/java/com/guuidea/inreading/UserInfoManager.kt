package com.guuidea.inreading

/**
 * @file      UserInfoManager
 * @description    用户信息管理（全局单例类）
 * @author         江 杰
 * @createDate     2020/11/12 10:31
 */
object UserInfoManager {
    private var userInfo: UserInfo? = null

    /**
     * 仅在登录成功后赋值，其余情况下均为空
     */
    fun saveUserInfo(info: UserInfo) {
        this.userInfo = info
    }

    fun getCurrentInfo(): UserInfo? {
        return userInfo
    }
}

/**
 * 用户信息Bean
 */
data class UserInfo(
        var name: String,
        var isVIP: Boolean,
        var userId: String,
        var vipExpireDate: String
)