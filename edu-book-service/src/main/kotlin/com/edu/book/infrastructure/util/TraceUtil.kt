package com.edu.book.infrastructure.util

import com.edu.book.infrastructure.constants.Constants.TRACE_ID
import org.slf4j.MDC

/**
 * @Auther: liukaihua
 * @Date: 2024/3/7 10:29
 * @Description:
 */
object TraceUtil {

    /**
     * 创建traceId
     */
    fun createTraceId() {
        val traceId = MDC.get(TRACE_ID)
        if (traceId.isNullOrBlank()) {
            val newTraceId = UUIDUtil.createUUID()
            MDC.put(TRACE_ID, newTraceId)
        }
    }

    /**
     * 移除tarceId
     */
    fun removeTraceId() {
        MDC.remove(TRACE_ID)
    }

    /**
     * 获取traceId
     */
    fun getTraceId(): String? {
        return MDC.get(TRACE_ID)
    }

}