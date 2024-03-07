package com.edu.book.infrastructure.anno

import org.springframework.web.bind.annotation.ResponseBody

@MustBeDocumented
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@ResponseBody
annotation class Response
