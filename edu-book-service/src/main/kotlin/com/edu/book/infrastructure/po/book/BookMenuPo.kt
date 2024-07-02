package com.edu.book.infrastructure.po.book;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.edu.book.infrastructure.po.BasePo;

/**
 * <p>
 * 书单表
 * </p>
 *
 * @author 
 * @since 2024-07-02 23:32:18
 */
@TableName("t_book_menu")
class BookMenuPo : BasePo() {

    /**
     * 业务唯一id
     */
    @TableField("c_uid")
    var uid: String? = null

    /**
     * 图书Uid
     */
    @TableField("c_book_uid")
    var bookUid: String? = null

}
