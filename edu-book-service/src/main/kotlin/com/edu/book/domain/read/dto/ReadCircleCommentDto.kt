package com.edu.book.domain.read.dto

import java.io.Serializable

/**
 * @Auther: liukaihua
 * @Date: 2024/6/4 22:35
 * @Description:
 */
class ReadCircleCommentDto: Serializable {

    /**
     * 文本
     */
    var commentText: String = ""

    /**
     * 用户
     */
    var commentUserUid: String = ""

    /**
     * 用户
     */
    var commentUsername: String = ""

    /**
     * 昵称
     */
    var commentUserNickname: String = ""

    /**
     * 评论uid
     */
    var commentUid: String = ""

    /**
     * 被评论人
     */
    var commentedUserUid: String = ""

    /**
     * 被评论人
     */
    var commentedUsername: String = ""

    /**
     * 被评论人
     */
    var commentedUserNickname: String = ""

    /**
     * 父级评论UI
     */
    var parentCommentUid: String = ""

}