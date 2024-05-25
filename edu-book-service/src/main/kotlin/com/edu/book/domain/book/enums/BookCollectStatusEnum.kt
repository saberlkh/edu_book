package com.edu.book.domain.book.enums

/**
 * @Auther: liukaihua
 * @Date: 2024/5/25 21:03
 * @Description:
 */
enum class BookCollectStatusEnum(val status: Int) {

    /**
     * 已收藏
     */
    COLLECTED(1),

    /**
     * 取消收藏
     */
    UN_COLLECTED(0),

    ;

}