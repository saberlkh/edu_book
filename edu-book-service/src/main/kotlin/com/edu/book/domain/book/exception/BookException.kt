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

/**
 * 图书已经存在
 */
class BookDetailAlreadyExistException: BookException("图书已经存在")

/**
 * 图书不存在
 */
class BookDetailNotExistException: BookException("图书不存在")

/**
 * 不存在
 */
class BookInfoNotExistException: BookException("图书不存在")