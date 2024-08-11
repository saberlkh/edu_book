package com.edu.book.domain.book.dto

import java.io.Serializable
import org.apache.commons.lang3.math.NumberUtils

/**
 * @Auther: liukaihua
 * @Date: 2024/8/11 11:12
 * @Description:
 */
class ReservationBookPageResultDto: Serializable {

    var isbn: String = ""

    var title: String = ""

    var subtitle: String = ""

    var summary: String = ""

    var pic: String = ""

    var reservationUser: ReservationUserDto? = null

}

class ReservationUserDto: Serializable {

    var userUid: String = ""

    var nickname: String = ""

    var gardenUid: String = ""

    var gardenName: String = ""

}