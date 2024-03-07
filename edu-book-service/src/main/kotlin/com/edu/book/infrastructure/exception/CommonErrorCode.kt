package com.edu.book.infrastructure.exception

import org.springframework.http.HttpStatus

/**
 * @Auther: liukaihua
 * @Date: 2024/3/7 10:49
 * @Description:
 */
enum class CommonErrorCode(override val errorCode: Int,
                           override val errorMessage: String,
                           override val httpStatus: HttpStatus
): ErrorCode {

    SUCCESS(200, "Success", HttpStatus.OK), //成功
    ERROR(40000, "Error", HttpStatus.OK), //失败

}