package com.edu.book.infrastructure.exception

import org.springframework.http.HttpStatus

/**
 * @Auther: liukaihua
 * @Date: 2024/3/7 10:48
 * @Description:
 */
open class WebAppException : RuntimeException {

    private val httpStatus: HttpStatus
    private val errorCode: Int
    private var data: Any? = null

    constructor(error: ErrorCode) : super(error.errorMessage) {
        this.httpStatus = error.httpStatus
        this.errorCode = error.errorCode
    }

    constructor(errorCode: Int, message: String) : super(message) {
        this.errorCode = errorCode
        this.httpStatus = HttpStatus.OK
    }

    constructor(error: ErrorCode, reason: String, data: Any?): super(reason) {
        this.httpStatus = error.httpStatus
        this.errorCode = error.errorCode
        this.data = data
    }

    constructor(error: ErrorCode, reason: String) : super(reason) {
        this.httpStatus = error.httpStatus
        this.errorCode = error.errorCode
    }

    fun getHttpStatus(): HttpStatus {
        return this.httpStatus
    }

    fun getReasonCode(): Int {
        return this.errorCode
    }

    override fun toString(): String {
        return "[WebAppException] ==> httpStatus: ${this.httpStatus}, errorCode: ${this.errorCode}, message: ${super.message}"
    }

}

class WebAppExceptionWithStackTrace: WebAppException {
    private var stackTraceMsg: String? = null

    constructor(error: ErrorCode, stackTraceMsg: String?) : super(error) {
        this.stackTraceMsg = stackTraceMsg
    }

    fun getStackTraceMsg(): String?{
        return stackTraceMsg
    }
}