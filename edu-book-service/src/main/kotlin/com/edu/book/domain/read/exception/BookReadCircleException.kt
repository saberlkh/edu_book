package com.edu.book.domain.read.exception

import java.io.Serializable

/**
 * @Auther: liukaihua
 * @Date: 2024/6/4 23:50
 * @Description:
 */
open class BookReadCircleException: RuntimeException, Serializable {

    constructor()

    constructor(message: String): super(message)

}

/**
 * 阅读圈不存在
 */
class ReadCircleNotExistException: BookReadCircleException("阅读圈不存在")

/**
 * 不能修改不是自己创建的阅读圈
 */
class CanNotDeleteCircleException: BookReadCircleException("不能修改不是自己创建的阅读圈")