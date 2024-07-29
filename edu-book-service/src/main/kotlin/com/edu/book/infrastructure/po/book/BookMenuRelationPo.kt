package com.edu.book.infrastructure.po.book;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.edu.book.infrastructure.po.BasePo;

/**
 * <p>
 * 书单关联表
 * </p>
 *
 * @author 
 * @since 2024-07-28 22:04:12
 */
@TableName("t_book_menu_relation")
class BookMenuRelationPo : BasePo() {

    /**
     * 业务唯一id
     */
    @TableField("c_uid")
    var uid: String? = null

    /**
     * isbn
     */
    @TableField("c_isbn")
    var isbn: String? = null

    /**
     * c_book_menu_uid
     */
    @TableField("c_book_menu_uid")
    var bookMenuUid: String? = null

}
