package com.edu.book.infrastructure.po.book;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.edu.book.infrastructure.po.BasePo;

/**
 * <p>
 * 图书分类表
 * </p>
 *
 * @author 
 * @since 2024-03-25 23:01:25
 */
@TableName("t_book_detail_classify")
class BookDetailClassifyPo : BasePo() {

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
     * 分类
     */
    @TableField("c_classify")
    var classify: String? = null

}
