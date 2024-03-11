package com.edu.book.api.vo.exception

import com.alibaba.fastjson.annotation.JSONField
import com.edu.book.infrastructure.exception.WebAppException
import com.edu.book.infrastructure.exception.WebAppExceptionWithStackTrace
import com.edu.book.infrastructure.util.TraceUtil

class ErrorResponse {

    @JSONField(name = "code")
    val code: Int

    @JSONField(name = "message")
    val message: String

    @JSONField(name = "traceId")
    val traceId: String?

    @JSONField(name = "stackTraceMsg")
    var stackTraceMsg: String? = null

    @JSONField(name = "data")
    var data: Any? = null

    constructor(code: Int, message: String) {
        this.code = code
        this.message = message
        this.traceId = TraceUtil.getTraceId()
    }

    constructor(ex: WebAppException) {
        this.code = ex.getReasonCode()
        this.message = ex.localizedMessage
        this.traceId = TraceUtil.getTraceId()
        this.stackTraceMsg = if (ex is WebAppExceptionWithStackTrace) ex.getStackTraceMsg() else null
    }

    constructor(ex: WebAppException, data: Any?) {
        this.code = ex.getReasonCode()
        this.message = ex.localizedMessage
        this.data = data
        this.traceId = TraceUtil.getTraceId()
    }
}