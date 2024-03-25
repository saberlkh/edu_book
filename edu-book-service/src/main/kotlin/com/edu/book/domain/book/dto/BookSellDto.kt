package com.edu.book.domain.book.dto

import java.io.Serializable

/**
 * @Auther: liukaihua
 * @Date: 2024/3/25 10:15
 * @Description:
 */
class BookSellDto: Serializable {

    /**
     * 平台
     */
    var seller: String? = null

    /**
     * 价格
     */
    var price: Int? = null

    /**
     * 连接
     */
    var link: String? = null

}