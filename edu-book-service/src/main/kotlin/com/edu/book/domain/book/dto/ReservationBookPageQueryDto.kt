package com.edu.book.domain.book.dto

import java.io.Serializable
import javax.validation.constraints.NotBlank

/**
 * @Auther: liukaihua
 * @Date: 2024/8/11 12:38
 * @Description:
 */
class ReservationBookPageQueryDto: Serializable {

    @NotBlank(message = "幼儿园不能为空")
    var kindergartenUid: String = ""

    var page: Int = 0

    var pageSize: Int = 20

    var userUid: String = ""

}