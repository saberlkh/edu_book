package com.edu.book.domain.book.dto

import java.io.Serializable

/**
 * @Auther: liukaihua
 * @Date: 2024/5/30 15:28
 * @Description:
 */
class ReturnBookDto: Serializable {

    /**
     * Uid
     */
    var bookUid: String = ""

    /**
     * 用户Uid
     */
    var userUid: String = ""

    /**
     * 图书状态
     * @see com.edu.book.domain.book.enums.BookDetailStatusEnum
     */
    var bookStatus: Int? = null

}