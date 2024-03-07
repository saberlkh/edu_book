package com.edu.book.infrastructure.response

import com.edu.book.infrastructure.anno.Response
import com.edu.book.infrastructure.exception.ErrorCodeMapUtil
import com.edu.book.infrastructure.exception.ErrorCodeMapUtil.SUCCESS
import com.edu.book.infrastructure.util.DataMap
import org.springframework.core.MethodParameter
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice

/**
 * @Auther: liukaihua
 * @Date: 2024/3/7 10:45
 * @Description:
 */
@ControllerAdvice(annotations = [Response::class])
class ResponseResultHandlerAdvice : ResponseBodyAdvice<Any> {

    override fun supports(p0: MethodParameter, p1: Class<out HttpMessageConverter<*>>): Boolean {
        return true
    }

    override fun beforeBodyWrite(body: Any?, methodParameter: MethodParameter,
                                 mediaType: MediaType, contentType: Class<out HttpMessageConverter<*>>,
                                 req: ServerHttpRequest,
                                 res: ServerHttpResponse
    ): Any? {
        //不是json格式响应不做处理
        if (MediaType.APPLICATION_JSON != mediaType || body is DataMap || body is ResponseVo<*>) {
            return body
        }
        return ResponseVo(ErrorCodeMapUtil.getErrorCode(SUCCESS), body)
    }

}