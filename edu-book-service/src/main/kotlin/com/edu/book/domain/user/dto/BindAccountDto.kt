package com.edu.book.domain.user.dto

import java.io.Serializable

/**
 * @Auther: liukaihua
 * @Date: 2024/3/14 21:49
 * @Description:
 */
class BindAccountDto: Serializable {

    /**
     * openid
     */
    var openId: String = ""

    /**
     * 手机号
     */
    var phone: String = ""

    /**
     * 账户Uid
     */
    var accountUid: String = ""

    /**
     * unionid
     */
    var unionId: String = ""

}