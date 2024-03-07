package com.edu.book.api.http.config

import com.edu.book.api.http.aspect.WebReqLogAspect
import com.edu.book.api.http.interceptor.TraceIdInterceptor
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

    @Bean
    fun traceIdInterceptor(): TraceIdInterceptor {
        return TraceIdInterceptor()
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
        super.addInterceptors(registry)
    }

}