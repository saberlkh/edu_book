package com.edu.book.infrastructure.response

import com.edu.book.infrastructure.exception.ErrorCode
import com.edu.book.infrastructure.exception.ErrorCodeMapUtil
import com.edu.book.infrastructure.exception.WebAppException
import com.edu.book.infrastructure.exception.WebAppExceptionWithStackTrace
import com.edu.book.infrastructure.util.TraceUtil

/**
 * @Auther: liukaihua
 * @Date: 2024/3/7 10:46
 * @Description:
 */
class ResponseVo<T> {

    var code: Any? = null
    var message: String? = null
    var data: T? = null
    var traceId: String? = null
    var stackTraceMsg: String? = null

    constructor()

    constructor(data: T?) {
        this.code = ErrorCodeMapUtil.getSuccessErrorCode().errorCode
        this.message = ErrorCodeMapUtil.getSuccessErrorCode().errorMessage
        this.data = data
        this.traceId = TraceUtil.getTraceId()
    }

    constructor(errorCode: ErrorCode, data: T?) {
        this.code = errorCode.errorCode
        this.message = errorCode.errorMessage
        this.data = data
        this.traceId = TraceUtil.getTraceId()
    }

    constructor(ex: WebAppException) {
        this.code = ex.getReasonCode()
        this.message = ex.localizedMessage
        this.traceId = TraceUtil.getTraceId()
        this.stackTraceMsg = if(ex is WebAppExceptionWithStackTrace) ex.getStackTraceMsg() else null
    }

}