package com.edu.book.domain.book.dto

import java.io.Serializable
import javax.validation.constraints.NotBlank

/**
 * @Auther: liukaihua
 * @Date: 2024/8/27 20:22
 * @Description:
 */
class CancelReservationBookDto: Serializable {

    /**
     * 用户Uid
     */
    var userUid: String = ""

    /**
     * isbn
     */
    @NotBlank(message = "isbn不能为空")
    var isbn: String = ""

}