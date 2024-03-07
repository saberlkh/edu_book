package com.edu.book.infrastructure.exception

import org.springframework.http.HttpStatus

/**
 * @Auther: liukaihua
 * @Date: 2024/3/7 10:48
 * @Description:
 */
interface ErrorCode {
    //HTTP状态码
    val httpStatus: HttpStatus
    //具体错误原因码
    val errorCode: Int
    //错误简要说明
    val errorMessage: String
}