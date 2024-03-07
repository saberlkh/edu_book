package com.edu.book.api.http.aspect

import com.edu.book.api.http.anno.WebReqLog
import com.edu.book.api.http.anno.WebReqUnLog
import com.fasterxml.jackson.databind.ObjectMapper
import java.lang.reflect.Method
import javax.servlet.ServletRequest
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.aopalliance.intercept.MethodInterceptor
import org.aopalliance.intercept.MethodInvocation
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Pointcut
import org.slf4j.LoggerFactory
import org.springframework.web.context.request.RequestAttributes
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.servlet.mvc.method.annotation.ExtendedServletRequestDataBinder

/**
 * @Auther: liukaihua
 * @Date: 2024/3/7 10:37
 * @Description:
 */
class WebReqLogAspect : MethodInterceptor {

    companion object {
        private val log = LoggerFactory.getLogger(WebReqLogAspect::class.java)
        private const val RESULT_MAX_LENGTH = 3000
        private val objectMapper = ObjectMapper()
    }

    @Pointcut("@annotation(com.seewo.live.web.log.WebReqLog)")
    fun webReqLog() {
    }

    @Around("webReqLog()")
    fun log(joinPoint: ProceedingJoinPoint): Any? {
        val signature = joinPoint.signature
        val clazz = signature.declaringType
        val methodName = signature.toShortString()
        val startIdx = methodName.indexOf(".") + 1
        val endIdx = methodName.indexOf("(")
        val method = methodName.substring(startIdx, endIdx)
        val args = joinPoint.args.map { it.javaClass }
        val realMethod = clazz.getDeclaredMethod(method, * args.toTypedArray())

        val preProcessLog = buildPreProcessLog(realMethod, joinPoint.args)
        return try {
            val start = System.currentTimeMillis()
            val result = joinPoint.proceed()

            val afterProcessLog = buildAfterProcessLog(result, start)
            log.info("$preProcessLog$afterProcessLog")

            result
        } catch (e: Throwable) {
            log.info(preProcessLog)
            throw e
        }

    }

    override fun invoke(invocation: MethodInvocation): Any? {

        if (invocation.method.getAnnotation(WebReqLog::class.java) != null
            || invocation.method.getAnnotation(WebReqUnLog::class.java) != null) {
            return invocation.proceed()
        }

        val preProcessLog = buildPreProcessLog(invocation.method, invocation.arguments)

        return try {
            val start = System.currentTimeMillis()
            val result = invocation.proceed()
            val afterProcessLog = buildAfterProcessLog(result, start)
            log.info("$preProcessLog$afterProcessLog")
            result
        } catch (e: Throwable) {
            log.info(preProcessLog)
            throw e
        }

    }

    private fun buildPreProcessLog(method: Method, args: Array<Any?>?): String {
        val sb = StringBuilder()
        val request = RequestContextHolder.currentRequestAttributes().resolveReference(RequestAttributes.REFERENCE_REQUEST) as HttpServletRequest
        sb.append("\n").append("请求Web接口日志:\n")
        sb.append("clientIp: ${request.remoteAddr}")
        sb.append("method: ${request.method}\n")
        sb.append("url: ${request.requestURL}\n")
        sb.append("header: [")
        for (headerName in request.headerNames) {
            val headerValue = request.getHeader(headerName)
            if (!headerValue.isNullOrBlank()) {
                sb.append("${headerName}: ${headerValue},")
            }
        }
        sb.deleteCharAt(sb.length - 1).append("]\n")

        sb.append("class#method: ").append(method.name).append("\n")
        sb.append("params: [")
        method.parameters.forEachIndexed { index, parameter ->
            val arg = args!![index]
            if (arg is Class<*> || arg is ServletRequest
                || arg is HttpServletRequest || arg is HttpServletResponse
            ) {
                return@forEachIndexed
            }
            if (arg is ExtendedServletRequestDataBinder) {
                sb.append("${parameter.name}: ${objectMapper.writeValueAsString(arg.target)},")
                return@forEachIndexed
            }
            try {
                sb.append("${parameter.name}: ${objectMapper.writeValueAsString(arg)},")
            } catch (ex: Exception) {
                // 防止其他未捕获的异常导致请求无法正常被处理
                log.error("web request log occur unknown exception {}", ex.message)
            }
        }

        sb.deleteCharAt(sb.length - 1).append("]")
        return sb.toString()
    }

    private fun buildAfterProcessLog(result: Any?, startProcessTime: Long): String {
        val sb = StringBuilder()
        sb.append("\n接口用时(ms): ").append(System.currentTimeMillis() - startProcessTime)
        var resultStr: String? = ""
        if (result != null || result !is ServletRequest || !result::class.java.isInstance(Unit::class)) {
            resultStr = objectMapper.writeValueAsString(result)
        }
        if (resultStr!!.length >= RESULT_MAX_LENGTH) {
            resultStr = "${resultStr.substring(0, RESULT_MAX_LENGTH)}..."
        }
        sb.append("\n").append("返回值:\n").append(resultStr)
        return sb.toString()
    }

}