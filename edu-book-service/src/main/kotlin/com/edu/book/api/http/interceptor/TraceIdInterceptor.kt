package com.edu.book.api.http.interceptor

import com.edu.book.infrastructure.util.TraceUtil
import java.lang.Exception
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.web.servlet.HandlerInterceptor

/**
 * @Auther: liukaihua
 * @Date: 2024/3/7 10:34
 * @Description:
 */
class TraceIdInterceptor: HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        TraceUtil.createTraceId()
        return true
    }

    override fun afterCompletion(request: HttpServletRequest, response: HttpServletResponse, handler: Any, ex: Exception?) {
        TraceUtil.removeTraceId()
    }

}