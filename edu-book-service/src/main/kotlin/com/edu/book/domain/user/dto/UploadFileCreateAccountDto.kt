package com.edu.book.domain.user.dto

import java.io.Serializable

/**
 * @Auther: liukaihua
 * @Date: 2024/5/12 17:13
 * @Description:
 */
class UploadFileCreateAccountDto: Serializable {

    /**
     * 教室Uid
     */
    var classUid: String = ""

    var accountList: List<CreateAccountDto> = emptyList()

}

class CreateAccountDto: Serializable {

    var studentName: String = ""

    var openBorrowService: Boolean = false

    var parentPhone: String = ""

}