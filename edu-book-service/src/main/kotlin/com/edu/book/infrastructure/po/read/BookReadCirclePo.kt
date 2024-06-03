package com.edu.book.infrastructure.po.read;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.edu.book.infrastructure.po.BasePo;

/**
 * <p>
 * 阅读圈表
 * </p>
 *
 * @author 
 * @since 2024-06-03 22:47:56
 */
@TableName("t_book_read_circle")
class BookReadCirclePo : BasePo() {

    /**
     * 业务唯一id
     */
    @TableField("c_uid")
    var uid: String? = null

    /**
     * 文本内容
     */
    @TableField("c_read_text")
    var readText: String? = null

    /**
     * 用户Uid
     */
    @TableField("c_user_uid")
    var userUid: String? = null

    /**
     * 账号Uid
     */
    @TableField("c_account_uid")
    var accountUid: String? = null

    /**
     * 园区Uid
     */
    @TableField("c_garden_uid")
    var gardenUid: String? = null

}
