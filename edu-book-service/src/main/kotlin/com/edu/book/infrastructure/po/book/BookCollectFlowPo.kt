package com.edu.book.infrastructure.po.book;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.edu.book.infrastructure.po.BasePo;

/**
 * <p>
 * 图书收藏表
 * </p>
 *
 * @author 
 * @since 2024-05-25 20:53:04
 */
@TableName("t_book_collect_flow")
class BookCollectFlowPo : BasePo() {

    /**
     * 业务唯一id
     */
    @TableField("c_uid")
    var uid: String? = null

    /**
     * isbn
     */
    @TableField("c_isbn_code")
    var isbnCode: String? = null

    /**
     * 图书自编码
     */
    @TableField("c_book_uid")
    var bookUid: String? = null

    /**
     * 用户uid
     */
    @TableField("c_user_uid")
    var userUid: String? = null

    /**
     * 账户Uid
     */
    @TableField("c_account_uid")
    var accountUid: String? = null

    /**
     * 收藏状态
     */
    @TableField("c_collect_status")
    var collectStatus: Boolean? = null

}
