package com.edu.book.domain.book.dto

import java.io.Serializable
import javax.validation.constraints.NotBlank

/**
 * @Auther: liukaihua
 * @Date: 2024/8/8 22:47
 * @Description:
 */
class ReservationBookDto: Serializable {

    /**
     * 用户Uid
     */
    var userUid: String = ""

    /**
     * 借书卡Id
     */
    @NotBlank(message = "借书卡Id不能为空")
    var borrowCardId: String = ""

    /**
     * isbn
     */
    @NotBlank(message = "isbn不能为空")
    var isbn: String = ""

}