package com.edu.book.infrastructure.interceptor

import com.edu.book.infrastructure.config.SqlInterceptorConfig
import com.edu.book.infrastructure.dto.ConnectionPoolInfo
import com.edu.book.infrastructure.util.BeanFactoryUtil
import com.google.common.collect.HashMultiset
import com.google.common.collect.Multiset
import com.zaxxer.hikari.HikariDataSource
import org.apache.ibatis.cache.CacheKey
import org.apache.ibatis.executor.Executor
import org.apache.ibatis.mapping.BoundSql
import org.apache.ibatis.mapping.MappedStatement
import org.apache.ibatis.mapping.SqlCommandType
import org.apache.ibatis.plugin.Intercepts
import org.apache.ibatis.plugin.Invocation
import org.apache.ibatis.plugin.Plugin
import org.apache.ibatis.plugin.Signature
import org.apache.ibatis.session.ResultHandler
import org.apache.ibatis.session.RowBounds
import org.slf4j.LoggerFactory
import java.util.concurrent.Executors
import javax.sql.DataSource


/**
 * @author guhao
 * Date 2022/4/25
 *
 * 打印耗时比较久的慢查询SQL
 *
 * SqlExecuteTimeCountInterceptor 先于 SqlStatementInterceptor执行
 */
@Intercepts(
        value = [Signature(
                type = Executor::class,
                method = "query",
                args = [
                    MappedStatement::class,
                    Object::class,
                    RowBounds::class,
                    ResultHandler::class,
                    CacheKey::class,
                    BoundSql::class]
        ),
            Signature(
                    type = Executor::class,
                    method = "query",
                    args = [
                        MappedStatement::class,
                        Object::class,
                        RowBounds::class,
                        ResultHandler::class]
            ),
            Signature(
                    type = Executor::class,
                    method = "update",
                    args = [
                        MappedStatement::class,
                        Object::class]
            )]
)
class SqlExecuteTimeCountInterceptor : AbstractSqlInterceptor {

    private val log = LoggerFactory.getLogger(SqlExecuteTimeCountInterceptor::class.java)

    private val sqlLogPrinter: SQLLogPrinter

    constructor(sqlInterceptorConfig: SqlInterceptorConfig) {
        this.sqlInterceptorConfig = sqlInterceptorConfig
        this.sqlLogPrinter = SQLLogPrinter(sqlInterceptorConfig)
    }

    companion object {

        private val executor = Executors.newSingleThreadExecutor()

        /**
         * 获取连接池信息
         */
        fun getConnectionPoolInfo(): ConnectionPoolInfo {
            // 数据库连接池信息
            val dataSource = BeanFactoryUtil.getBean(DataSource::class.java) as HikariDataSource
            val poolMxBean = dataSource.hikariPoolMXBean
            val totalConnections = poolMxBean.totalConnections
            val activeConnections = poolMxBean.activeConnections
            val idleConnections = poolMxBean.idleConnections
            val threadsAwaitingConnection = poolMxBean.threadsAwaitingConnection

            return ConnectionPoolInfo(totalConnections, activeConnections, idleConnections, threadsAwaitingConnection)
        }
    }

    override fun beforeProcess(invocation: Invocation) {
        // 记录sql开始时间
        val startTime = System.currentTimeMillis()
        SqlHolder.setStartTime(startTime)

        // 获取 mappedStatement
        val mappedStatement = invocation.args[0] as MappedStatement

        // 打印所有sql
        val sql = sqlLogPrinter.printSql(invocation, mappedStatement)

        // 将sql设置上下文
        SqlHolder.setSql(sql)

        // 校验sql合法性(多表join 的情况)
        checkJoinSqL(mappedStatement, sql, sqlInterceptorConfig)
    }

    private fun checkJoinSqL(mappedStatement: MappedStatement, sql: String, sqlInterceptorConfig: SqlInterceptorConfig): Boolean {
        try {
            if (!sqlInterceptorConfig.checkSqlJoin) {
                return true
            }

            // 只校验SELECT语句
            if (mappedStatement.sqlCommandType != SqlCommandType.SELECT) {
                return false
            }

            val sqlWords = sql.toLowerCase().split(" ")
            val set: Multiset<String> = HashMultiset.create()
            for (word in sqlWords) {
                if (word.trim().isBlank()) {
                    continue
                }
                set.add(word.trim())
            }

            val joinNum = set.count("join")
            if (joinNum > 3) {
                log.warn("sql 中包含多表join")
            }
            return true
        } catch (e: Exception) {

        }

        return true
    }


    override fun afterProcess(invocation: Invocation, result: Any) {
        val endTime = System.currentTimeMillis()
        val startTime = SqlHolder.getStartTime()

        // 执行sql总耗时（获取连接时间 + sql执行时间（sql执行 + 网络传输））
        val costTime = endTime - startTime

        // 获取执行的方法
        val firstArg = invocation.args[0]
        val mappedStatement = firstArg as MappedStatement

        // sql执行时间（sql执行 + 网络传输）
        val methodId = mappedStatement.id
        val fetchConnectionTime = SqlHolder.getFetchConnectTime()
        val executeSqlTime = costTime - (fetchConnectionTime ?: 0)

        // 走到这里说明获取数据库连接超时
        if (fetchConnectionTime == null) {
            log.info("【获取不到MySql连接】methodId = $methodId")
            val connectionPoolInfo = getConnectionPoolInfo()

            // 二次校验，连接用完且有等待线程
        }

        // 发送慢sql告警
        else if (executeSqlTime >= sqlInterceptorConfig.minCost!!) {
            val sql = SqlHolder.getSql()

            log.info("【慢查询】总耗时：$costTime 毫秒 \n 【获取连接耗时】：$fetchConnectionTime \n " +
                    "方法id = $methodId, sql = \n $sql")
        }

        // 清除线程变量副本
        SqlHolder.clear()
    }

    override fun plugin(target: Any): Any {
        if (target is Executor) {
            return Plugin.wrap(target, this)
        }
        return target
    }
}


