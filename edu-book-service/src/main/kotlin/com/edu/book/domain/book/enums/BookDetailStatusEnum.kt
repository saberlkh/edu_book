package com.edu.book.domain.book.enums

/**
 * @Auther: liukaihua
 * @Date: 2024/3/25 19:41
 * @Description:
 */
enum class BookDetailStatusEnum(val status: Int, val desc: String) {

    IN_STORAGE(0, "入库"),

    BORROWED(1, "借阅中"),

    LOSS(2, "损耗")

    ;

}