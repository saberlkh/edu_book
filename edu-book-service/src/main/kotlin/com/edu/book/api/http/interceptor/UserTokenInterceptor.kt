package com.edu.book.api.http.interceptor

import com.edu.book.api.http.common.CurrentHolder
import com.edu.book.api.http.common.HttpConstant.TOKEN
import com.edu.book.application.service.UserAppService
import com.edu.book.infrastructure.enums.ErrorCodeConfig
import com.edu.book.infrastructure.exception.WebAppException
import java.lang.Exception
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.servlet.HandlerInterceptor

/**
 * @Auther: liukaihua
 * @Date: 2024/3/18 15:18
 * @Description: 用户鉴权拦截器
 */
class UserTokenInterceptor: HandlerInterceptor {

    @Autowired
    private lateinit var userAppService: UserAppService

    /**
     * 前置校验
     */
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val token = request.getHeader(TOKEN)
        if (token.isNullOrBlank()) throw WebAppException(ErrorCodeConfig.INVALID_TOKEN)
        val userDto = userAppService.authUser(token)
        CurrentHolder.initUserDtoLocal(userDto)
        return super.preHandle(request, response, handler)
    }

    override fun afterCompletion(request: HttpServletRequest, response: HttpServletResponse, handler: Any, ex: Exception?) {
        CurrentHolder.clearUserDtoLocal()
        super.afterCompletion(request, response, handler, ex)
    }

}