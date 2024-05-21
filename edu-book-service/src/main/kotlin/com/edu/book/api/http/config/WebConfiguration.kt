package com.edu.book.api.http.config

import com.edu.book.api.http.aspect.WebReqLogAspect
import com.edu.book.api.http.interceptor.TraceIdInterceptor
import com.edu.book.api.http.interceptor.UserTokenInterceptor
import org.springframework.aop.aspectj.AspectJExpressionPointcut
import org.springframework.aop.support.DefaultPointcutAdvisor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * @Auther: liukaihua
 * @Date: 2024/3/7 10:36
 * @Description:
 */

@Configuration
class WebConfiguration: WebMvcConfigurer {

    private val traceExecution = "execution(* com.edu.book.api.http.controller..*.*(..))"

    private final val openPath = listOf(
        "/wechat/**", "/user/v1/login", "/test/**", "/verification.html", "/hair/v1/admin/login", "/hair/v1/classify",
        "/hair/v1/classify/detail", "/book/v1/page", "/book/v1/**/detail"
    )

    @Bean
    fun traceIdInterceptor(): TraceIdInterceptor {
        return TraceIdInterceptor()
    }

    @Bean
    fun userTokenInterceptor(): UserTokenInterceptor {
        return UserTokenInterceptor()
    }

    @Bean
    @Primary
    fun defaultPointcutAdvisor(): DefaultPointcutAdvisor {
        val advice = WebReqLogAspect()
        val pointcut = AspectJExpressionPointcut()
        pointcut.expression = traceExecution
        // 配置增强类advisor
        val advisor = DefaultPointcutAdvisor()
        advisor.pointcut = pointcut
        advisor.advice = advice
        return advisor
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(traceIdInterceptor()).addPathPatterns("/**")
        registry.addInterceptor(userTokenInterceptor()).addPathPatterns("/**").excludePathPatterns(openPath)
        super.addInterceptors(registry)
    }

}