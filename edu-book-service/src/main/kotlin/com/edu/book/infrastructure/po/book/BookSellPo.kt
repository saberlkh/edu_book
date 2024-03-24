package com.edu.book.infrastructure.po.book;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.edu.book.infrastructure.po.BasePo;

/**
 * <p>
 * 图书售卖表
 * </p>
 *
 * @author 
 * @since 2024-03-24 22:54:53
 */
@TableName("t_book_sell")
class BookSellPo : BasePo() {

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
     * 平台
     */
    @TableField("c_seller")
    var seller: String? = null

    /**
     * 价格
     */
    @TableField("c_price")
    var price: Int? = null

    /**
     * 连接
     */
    @TableField("c_link")
    var link: String? = null

}
