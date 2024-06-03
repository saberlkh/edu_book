package com.edu.book.infrastructure.po.read;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.edu.book.infrastructure.po.BasePo;

/**
 * <p>
 * 阅读圈点赞流水表
 * </p>
 *
 * @author 
 * @since 2024-06-03 22:47:56
 */
@TableName("t_book_read_circle_comment_flow")
class BookReadCircleCommentFlowPo : BasePo() {

    /**
     * 业务唯一id
     */
    @TableField("c_uid")
    var uid: String? = null

    /**
     * uid
     */
    @TableField("c_read_circle_uid")
    var readCircleUid: String? = null

    /**
     * 评论用户Uid
     */
    @TableField("c_comment_user_uid")
    var commentUserUid: String? = null

    /**
     * 被评论用户
     */
    @TableField("c_commented_user_uid")
    var commentedUserUid: String? = null

    /**
     * 评论内容
     */
    @TableField("c_comment")
    var comment: String? = null

    /**
     * 父级评论Uid 可为空 不为空代表评论某条评论
     */
    @TableField("c_parent_comment_uid")
    var parentCommentUid: String? = null

}
