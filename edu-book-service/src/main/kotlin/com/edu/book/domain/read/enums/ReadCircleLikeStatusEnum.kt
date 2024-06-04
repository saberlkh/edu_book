package com.edu.book.domain.read.enums

/**
 * @Auther: liukaihua
 * @Date: 2024/6/4 23:00
 * @Description:
 */
enum class ReadCircleLikeStatusEnum(val status: Int) {

    /**
     * 点赞
     */
    LIKE(1),

    /**
     * 取消点赞
     */
    UN_LIKE(0),

    ;

}