package com.edu.book.infrastructure.interceptor

import com.edu.book.infrastructure.config.SqlInterceptorConfig
import java.sql.Statement
import org.apache.ibatis.executor.statement.StatementHandler
import org.apache.ibatis.plugin.Intercepts
import org.apache.ibatis.plugin.Invocation
import org.apache.ibatis.plugin.Signature
import org.apache.ibatis.session.ResultHandler

/**
 * @Auther: liukaihua
 * @Date: 2024/3/2 22:44
 * @Description:
 */
@Intercepts(
    value = [Signature(
        type = StatementHandler::class,
        method = "query",
        args = [Statement::class, ResultHandler::class]
    ),
        Signature(
            type = StatementHandler::class,
            method = "update",
            args = [Statement::class]
        )]
)
class SqlStatementInterceptor : AbstractSqlInterceptor {

    private val sqlLogPrinter: SQLLogPrinter

    constructor(sqlInterceptorConfig: SqlInterceptorConfig) {
        this.sqlInterceptorConfig = sqlInterceptorConfig
        this.sqlLogPrinter = SQLLogPrinter(sqlInterceptorConfig)
    }

    /**
     * 【如果获取连接超时，不会走到下面方法】
     * 1.获取数据库连接
     * 2.获取连接后执行下面方法
     */
    override fun beforeProcess(invocation: Invocation) {
        // 走到这里，已经获取到了连接
        val startTime = System.currentTimeMillis()
        SqlHolder.setFetchConnectTime(startTime - SqlHolder.getStartTime())
    }

    override fun afterProcess(invocation: Invocation, result: Any) {
        try {
            sqlLogPrinter.printResult(result)
        } catch (e: Exception) {

        }
    }
}