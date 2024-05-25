package com.edu.book.domain.book.dto

import java.io.Serializable

/**
 * @Auther: liukaihua
 * @Date: 2024/5/25 21:04
 * @Description:
 */
class CollectBookDto: Serializable {

    /**
     * 状态
     */
    var collectStatus: Int? = null

    /**
     * 用户uid
     */
    var userUid: String = ""

    /**
     * 图书Uid
     */
    var bookUid: String = ""

}