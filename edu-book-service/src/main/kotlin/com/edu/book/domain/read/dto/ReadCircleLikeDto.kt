package com.edu.book.domain.read.dto

import java.io.Serializable

/**
 * @Auther: liukaihua
 * @Date: 2024/6/4 22:34
 * @Description:
 */
class ReadCircleLikeDto: Serializable {

    /**
     * 点赞用户
     */
    var likeUserUid: String = ""

    /**
     * 用户名
     */
    var likeUsername: String = ""

    /**
     * 昵称
     */
    var likeUserNickname: String = ""

}