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
@TableName("t_book_read_circle_like_flow")
class BookReadCircleLikeFlowPo : BasePo() {

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
     * 点赞用户Uid
     */
    @TableField("c_user_uid")
    var userUid: String? = null

    /**
     * 点赞状态 0 取消点赞  1 点赞
     */
    @TableField("c_like_status")
    var likeStatus: Int? = null

}
