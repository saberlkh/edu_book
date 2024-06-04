package com.edu.book.api.vo.read

import java.io.Serializable

/**
 * @Auther: liukaihua
 * @Date: 2024/6/4 23:37
 * @Description:
 */
class ReadCircleLikeVo: Serializable {

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