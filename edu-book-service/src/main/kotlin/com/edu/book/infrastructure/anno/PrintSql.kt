package com.edu.book.infrastructure.anno


@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class PrintSql(
        val open: Boolean = true
)