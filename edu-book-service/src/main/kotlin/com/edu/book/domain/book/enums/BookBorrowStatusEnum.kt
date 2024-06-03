package com.edu.book.domain.book.enums

/**
 * @Auther: liukaihua
 * @Date: 2024/5/14 23:18
 * @Description:
 */
enum class BookBorrowStatusEnum(val status: Int) {

    /**
     * 借阅中
     */
    BORROWER(0),

    /**
     * 已经归还
     */
    RETURN(1),

    /**
     * 即将到期
     */
    ABOUT_TO_EXPIRE(2),

    /**
     * 超时未归还
     */
    EXPIRE_TO_RETURN(3),

    ;

}