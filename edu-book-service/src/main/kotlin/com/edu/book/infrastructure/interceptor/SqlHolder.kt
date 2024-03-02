package com.edu.book.infrastructure.interceptor

import com.edu.book.infrastructure.dto.SqlContext


object SqlHolder {

    private val sqlHolder: ThreadLocal<SqlContext> = ThreadLocal()

    fun getSql(): String {
        return sqlHolder.get().sql
    }

    fun clear() {
        return sqlHolder.remove()
    }

    fun setStartTime(startTime: Long) {
        if (sqlHolder.get() == null) {
            sqlHolder.set(SqlContext())
        }
        val context = sqlHolder.get() as SqlContext
        context.startTime = startTime
    }

    fun setSql(sql: String) {
        if (sqlHolder.get() == null) {
            sqlHolder.set(SqlContext())
        }
        sqlHolder.get().sql = sql
    }

    fun setFetchConnectTime(fetchConnectTime: Long) {
        if (sqlHolder.get() == null) {
            sqlHolder.set(SqlContext())
        }
        sqlHolder.get().fetchConnectTime = fetchConnectTime
    }

    fun getFetchConnectTime(): Long? {
        return sqlHolder.get().fetchConnectTime
    }

    fun getStartTime(): Long {
        return sqlHolder.get().startTime
    }

    fun getContext(): SqlContext {
        return sqlHolder.get()
    }
}