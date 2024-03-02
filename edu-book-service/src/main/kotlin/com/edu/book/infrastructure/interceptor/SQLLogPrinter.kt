package com.edu.book.infrastructure.interceptor

import com.alibaba.fastjson.JSON
import com.edu.book.infrastructure.anno.PrintSql
import com.edu.book.infrastructure.config.SqlInterceptorConfig
import org.apache.ibatis.mapping.MappedStatement
import org.apache.ibatis.mapping.ParameterMapping
import org.apache.ibatis.mapping.SqlCommandType
import org.apache.ibatis.plugin.Invocation
import org.apache.ibatis.reflection.MetaObject
import org.apache.ibatis.session.Configuration
import org.apache.ibatis.type.TypeHandlerRegistry
import org.slf4j.LoggerFactory
import java.text.DateFormat
import java.util.*


class SQLLogPrinter {

    private var sqlInterceptorConfig: SqlInterceptorConfig

    private val log = LoggerFactory.getLogger(SQLLogPrinter::class.java)

    companion object {
        // 正则表达式，替换空格、换行、tab缩进等
        private val SQL_TRIM_REGX1 = "[\\s]+".toRegex()
        private val SQL_TRIM_REGX2 = "\\?".toRegex()
        private val LOG_MAX_LENGTH = 1500
    }

    constructor(sqlInterceptorConfig: SqlInterceptorConfig) {
        this.sqlInterceptorConfig = sqlInterceptorConfig
    }

    fun printSql(invocation: Invocation, mappedStatement: MappedStatement): String {
        if (!sqlInterceptorConfig.logEnabled) {
            return ""
        }

        val parameter = invocation.args.getOrNull(1)
        val method = invocation.method

        // 判断方法或者方法所在类是否被 @PrintSql注解标记
        val printSql = method.getAnnotation(PrintSql::class.java)
                ?: method.declaringClass.getAnnotation(PrintSql::class.java)

        var sql = ""
        if (printSql != null && printSql.open) {
            sql = getSql(mappedStatement, parameter)
            log.info("【SQL】 ======> \n $sql")
            return sql
        }

        val sqlCommandType = mappedStatement.sqlCommandType
        sql = getSql(mappedStatement, parameter)
        if (this.sqlInterceptorConfig.excludeSelectSQL) {
            if (sqlCommandType != SqlCommandType.SELECT) {
                log.info("【SQL】 ======> \n $sql")
            }
        } else {
            log.info("【SQL】 ======> \n $sql")
        }

        return sql
    }

    fun printResult(result: Any) {
        try {
            //日志开起且开起了select则打日志，否则不打印，因为update、insert、delete返回的是int
            if (this.sqlInterceptorConfig.logEnabled && !this.sqlInterceptorConfig.excludeSelectSQL) {
                val resultJson = JSON.toJSONString(result) ?: ""
                if (resultJson.length > LOG_MAX_LENGTH) {
                    log.info("【result】 =========> ${resultJson.substring(0, LOG_MAX_LENGTH)}...")
                } else {
                    log.info("【result】 =========> $resultJson")
                }
            }

        } catch (e: Exception) {
        }
    }

    private fun getSql(mappedStatement: MappedStatement, parameter: Any?): String {
        val boundSql = mappedStatement.getBoundSql(parameter)
        val configuration: Configuration = mappedStatement.configuration
        val parameterObject = boundSql.parameterObject
        val parameterMappings: List<ParameterMapping> = boundSql.parameterMappings ?: emptyList()

        //替换空格、换行、tab缩进等
        var sql = boundSql.sql //.replace(SQL_TRIM_REGX1, "\n")
        if (parameterMappings.isNotEmpty() && parameterObject != null) {
            val typeHandlerRegistry: TypeHandlerRegistry = configuration.typeHandlerRegistry
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.javaClass)) {
                sql = sql.replaceFirst(SQL_TRIM_REGX1, getParameterValue(parameterObject))
            } else {
                val metaObject: MetaObject = configuration.newMetaObject(parameterObject)
                for (parameterMapping in parameterMappings) {
                    val propertyName: String = parameterMapping.property
                    if (metaObject.hasGetter(propertyName)) {
                        val obj: Any = metaObject.getValue(propertyName)
                        sql = sql.replaceFirst(SQL_TRIM_REGX2, getParameterValue(obj))
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        val obj = boundSql.getAdditionalParameter(propertyName)
                        sql = sql.replaceFirst(SQL_TRIM_REGX2, getParameterValue(obj))
                    }
                }
            }
            return sql
        }

        return boundSql.sql
    }

    private fun getParameterValue(obj: Any): String {
        val value = when (obj) {
            is String -> {
                "'$obj'"
            }

            is Date -> {
                val formatter: DateFormat = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA)
                "'" + formatter.format(Date()).toString() + "'"
            }

            else -> {
                obj.toString()
            }
        }
        return value.replace("$", "\\$")
    }
}