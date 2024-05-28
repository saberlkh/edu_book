package com.edu.book.domain.book.dto

import java.io.Serializable
import org.apache.commons.lang3.math.NumberUtils

/**
 * @Auther: liukaihua
 * @Date: 2024/5/23 22:22
 * @Description:
 */
class PageQueryBorrowBookDto: Serializable {

    /**
     * 用户
     */
    var userUid: String? = null

    /**
     * 园区
     */
    var gardenUid: String? = null

    /**
     * 书籍状态
     */
    var bookStatus: Int? = null

    /**
     * page
     */
    var page: Int = NumberUtils.INTEGER_ONE

    /**
     * pageSize
     */
    var pageSize: Int = 10

}