package com.edu.book.domain.user.dto

import java.io.Serializable
import org.apache.commons.lang3.math.NumberUtils

/**
 * @Auther: liukaihua
 * @Date: 2024/3/18 16:23
 * @Description:
 */
class UnbindAccountRespDto: Serializable {

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

}