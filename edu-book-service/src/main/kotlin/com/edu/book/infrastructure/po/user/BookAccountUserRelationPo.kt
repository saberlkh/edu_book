package com.edu.book.infrastructure.po.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.edu.book.infrastructure.po.BasePo;

/**
 * <p>
 * 账号用户关联表
 * </p>
 *
 * @author 
 * @since 2024-03-13 22:23:55
 */
@TableName("t_book_account_user_relation")
class BookAccountUserRelationPo : BasePo() {

    /**
     * 业务唯一id
     */
    @TableField("c_uid")
    var uid: String? = null

    /**
     * 账号Uid
     */
    @TableField("c_account_uid")
    var accountUid: String? = null

    /**
     * 用户Uid
     */
    @TableField("c_user_uid")
    var userUid: String? = null

}
