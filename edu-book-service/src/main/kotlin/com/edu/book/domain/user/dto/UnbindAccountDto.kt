package com.edu.book.domain.user.dto

import java.io.Serializable

/**
 * @Auther: liukaihua
 * @Date: 2024/3/18 15:50
 * @Description:
 */
class UnbindAccountDto: Serializable {

    /**
     * openid
     */
    var openId: String = ""

    /**
     * 手机号
     */
    var phone: String = ""

}