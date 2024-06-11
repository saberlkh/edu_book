package com.edu.book.api.vo.read

import java.io.Serializable
import javax.validation.constraints.NotBlank

/**
 * @Auther: liukaihua
 * @Date: 2024/6/11 21:15
 * @Description:
 */
class CommentLikeCircleVo: Serializable {

    /**
     * 阅读圈id
     */
    @NotBlank(message = "阅读圈id不能为空")
    var readCircleUid: String = ""

    /**
     * 评论内容
     */
    @NotBlank(message = "评论内容不能为空")
    var commentText: String = ""

    /**
     * 父级评论uid
     */
    var parentCommentUid: String = ""

    /**
     * 评论人
     */
    @NotBlank(message = "评论人不能为空")
    var commentUserUid: String = ""

    /**
     * 被评论人
     */
    @NotBlank(message = "被评论人不能为空")
    var commentedUserUid: String = ""

}