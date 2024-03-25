package com.edu.book.domain.book.exception

import java.io.Serializable

/**
 * @Auther: liukaihua
 * @Date: 2024/3/25 10:05
 * @Description:
 */
open class BookException: RuntimeException, Serializable {

    constructor()

    constructor(message: String): super(message)

}

/**
 * 查询isbn失败
 */
class QueryIsbnApiInfoErrorException: BookException("通过isbn的api查询图书信息失败")