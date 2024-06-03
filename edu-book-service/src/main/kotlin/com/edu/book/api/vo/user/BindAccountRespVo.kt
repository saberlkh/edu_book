package com.edu.book.api.vo.user

import java.io.Serializable
import org.apache.commons.lang3.math.NumberUtils

/**
 * @Auther: liukaihua
 * @Date: 2024/3/15 10:14
 * @Description:
 */
class BindAccountRespVo: Serializable {

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
     * 名称
     */
    var gardenName: String = ""

}