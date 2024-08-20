package com.edu.book.domain.user.dto

import java.io.Serializable
import org.apache.commons.lang3.math.NumberUtils

/**
 * @Auther: liukaihua
 * @Date: 2024/3/13 22:34
 * @Description:
 */
class RegisterUserDto: Serializable {

    /**
     * 是否绑定
     */
    var bind: Int = NumberUtils.INTEGER_ZERO

    /**
     * 手机
     */
    var phone: String = ""

    /**
     * 用户Uid
     */
    var userUid: String = ""

    /**
     * openId
     */
    var openId: String = ""

    /**
     * 用户名
     */
    var username: String = ""

    /**
     * nickname
     */
    var nickname: String = ""

    /**
     * token
     */
    var token: String = ""

    /**
     * 权限列表
     */
    var permissionList: List<String> = emptyList()

    /**
     * 角色
     */
    var roleCode: String = ""

    /**
     * 账户
     */
    var accountUid: String = ""

    /**
     * 过期时间
     */
    var accountExpireTime: Long = NumberUtils.LONG_ZERO

    /**
     * unionid
     */
    var unionId: String = ""

    /**
     * 园区uid
     */
    var gardenUid: String = ""

    /**
     * 园区Uid
     */
    var gardenName: String = ""

    /**
     * 头像
     */
    var photoUrl: String = ""

    /**
     * 幼儿园
     */
    var kindergartenUid: String = ""

    /**
     * 幼儿园
     */
    var kindergartenName: String = ""

    /**
     * 借阅卡id
     */
    var borrowCardId: String = ""

}