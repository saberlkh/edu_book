package com.edu.book.infrastructure.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

/**
 * @author guhao
 * Date 2022/4/24
 */
@Configuration
class SqlInterceptorConfig {

    /**
     * 是否开启插件，默认为true.
     */
    @Value("\${sql.plugin.enabled:true}")
    var enabled: Boolean = true

    /**
     * 是否开启记录SQL日志，默认为false.
     */
    @Value("\${sql.log.enabled:true}")
    var logEnabled: Boolean = true

    @Value("\${sql.exclude.select.log.enabled:true}")
    var excludeSelectSQL: Boolean = true

    /**
     * 记录执行时间超过多少毫秒的语句，默认1s，记录所有语句.
     */
    @Value("\${sql.log.minCost:2000}")
    var minCost: Int? = 2000

    /**
     * 企业微信机器人推送url后缀key
     */
    @Value("\${sql.log.web.hook.key:4c175633-20aa-445c-b7af-9239f4d5a9ab}")
    var webHookKey: String = ""

    /**
     * 是否校验select语句中的多表join情况
     */
    @Value("\${sql.log.check.sql.join:false}")
    var checkSqlJoin: Boolean = false
}