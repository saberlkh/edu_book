package com.edu.book.domain.book.dto

import java.io.Serializable

/**
 * @Auther: liukaihua
 * @Date: 2024/5/18 21:57
 * @Description:
 */
class BorrowBookDto: Serializable {

    /**
     * 借书卡Id
     */
    var borrowCardId: String = ""

    /**
     * 书籍uid
     */
    var bookUid: String = ""

}