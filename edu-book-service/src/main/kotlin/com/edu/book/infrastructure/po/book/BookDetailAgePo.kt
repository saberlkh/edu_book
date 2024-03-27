package com.edu.book.infrastructure.po.book;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.edu.book.infrastructure.po.BasePo;

/**
 * <p>
 * 图书年龄段
 * </p>
 *
 * @author 
 * @since 2024-03-27 20:38:19
 */
@TableName("t_book_detail_age")
class BookDetailAgePo : BasePo() {

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
     * 年龄段
     */
    @TableField("c_age_group")
    var ageGroup: Int? = null

}
