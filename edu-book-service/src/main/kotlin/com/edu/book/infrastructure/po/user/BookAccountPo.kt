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
     * 密码
     */
    @TableField("c_password")
    var password: String? = null

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

    /**
     * 学生名称
     */
    @TableField("c_student_name")
    var studentName: String? = null

    /**
     * 家长手机号
     */
    @TableField("c_parent_phone")
    var parentPhone: String? = null

    /**
     * 是否开通服务
     */
    @TableField("c_open_borrow_service")
    var openBorrowService: Boolean? = null

    /**
     * 押金
     */
    @TableField("c_cash_pledge")
    var cashPledge: Int? = null

    /**
     * 借阅卡id
     */
    @TableField("c_borrow_card_id")
    var borrowCardId: String? = null

    /**
     * 班级Uid
     */
    @TableField("c_class_uid")
    var classUid: String? = null

}
