package com.edu.book.infrastructure.interceptor

import com.baomidou.mybatisplus.extension.handlers.AbstractSqlParserHandler
import com.edu.book.infrastructure.config.SqlInterceptorConfig
import org.apache.ibatis.plugin.Interceptor
import org.apache.ibatis.plugin.Invocation
import org.slf4j.LoggerFactory

/**
 * @author guhao
 * Date 2022/7/13
 */
abstract class AbstractSqlInterceptor : AbstractSqlParserHandler(), Interceptor {

    private val log = LoggerFactory.getLogger(AbstractSqlInterceptor::class.java)

    var sqlInterceptorConfig: SqlInterceptorConfig = SqlInterceptorConfig()


    override fun intercept(invocation: Invocation): Any {
        // 前置处理
        if (sqlInterceptorConfig.enabled) {
            try {
                beforeProcess(invocation)
            } catch (e: Exception) {
                log.warn("*** beforeProcess sql 异常，errMsg = ${e.localizedMessage} ***")
            }
        }

        // 方法处理
        val result = invocation.proceed()

        // 后置处理
        if (sqlInterceptorConfig.enabled) {
            try {
                afterProcess(invocation, result)
            } catch (e: Exception) {
                log.warn("*** afterProcess sql 异常，errMsg = ${e.localizedMessage} ***")
            }
        }
        return result
    }

    abstract fun beforeProcess(invocation: Invocation)

    abstract fun afterProcess(invocation: Invocation, result: Any)

}