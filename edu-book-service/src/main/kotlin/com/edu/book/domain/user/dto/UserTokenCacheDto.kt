package com.edu.book.domain.user.dto

import java.io.Serializable

/**
 * @Auther: liukaihua
 * @Date: 2024/3/18 15:30
 * @Description:
 */
class UserTokenCacheDto: Serializable {

    /**
     * token
     */
    var token: String = ""

    /**
     * 用户Uid
     */
    var userUid: String = ""

}