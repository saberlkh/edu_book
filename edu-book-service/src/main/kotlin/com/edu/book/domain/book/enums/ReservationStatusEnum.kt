package com.edu.book.domain.book.enums

/**
 * @Auther: liukaihua
 * @Date: 2024/8/8 23:01
 * @Description:
 */
enum class ReservationStatusEnum(val status: Int) {

    /**
     * 预定中
     */
    Reservationing(0),

    /**
     * 已借阅
     */
    Borrowed(1),

    ;

}