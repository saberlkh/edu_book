package com.edu.book.infrastructure.po.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.edu.book.infrastructure.po.BasePo;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author 
 * @since 2024-03-13 22:23:55
 */
@TableName("t_book_user")
class BookUserPo : BasePo() {

    /**
     * 业务唯一id
     */
    @TableField("c_uid")
    var uid: String? = null

    /**
     * 姓名
     */
    @TableField("c_name")
    var name: String? = null

    /**
     * 昵称
     */
    @TableField("c_nick_name")
    var nickName: String? = null

    /**
     * 微信Uid
     */
    @TableField("c_wechat_uid")
    var wechatUid: String? = null

    /**
     * 手机号
     */
    @TableField("c_phone")
    var phone: String? = null

    /**
     * 关联账号
     */
    @TableField("c_associate_account")
    var associateAccount: String? = null

}
