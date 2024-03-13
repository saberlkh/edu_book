package com.edu.book.infrastructure.po.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.edu.book.infrastructure.po.BasePo;
import java.util.Date;

/**
 * <p>
 * 账号表
 * </p>
 *
 * @author 
 * @since 2024-03-13 22:23:55
 */
@TableName("t_book_account")
class BookAccountPo : BasePo() {

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
     * 账号名称
     */
    @TableField("c_account_name")
    var accountName: String? = null

    /**
     * 账号昵称
     */
    @TableField("c_account_nick_name")
    var accountNickName: String? = null

    /**
     * 账号过期时间
     */
    @TableField("c_expire_time")
    var expireTime: Date? = null

}
