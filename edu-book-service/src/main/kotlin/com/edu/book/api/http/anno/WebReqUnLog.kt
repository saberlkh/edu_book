package com.edu.book.api.http.anno

/**
 * @Auther: liukaihua
 * @Date: 2024/3/7 10:40
 * @Description:
 */
@MustBeDocumented
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class WebReqUnLog