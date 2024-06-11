package com.edu.book.domain.read.dto

import java.io.Serializable

/**
 * @Auther: liukaihua
 * @Date: 2024/6/5 20:20
 * @Description:
 */
class CommentLikeCircleDto: Serializable {

    /**
     * 阅读圈id
     */
    var readCircleUid: String = ""

    /**
     * 评论内容
     */
    var commentText: String = ""

    /**
     * 父级评论uid
     */
    var parentCommentUid: String = ""

    /**
     * 评论人
     */
    var commentUserUid: String = ""

    /**
     * 被评论人
     */
    var commentedUserUid: String = ""

}