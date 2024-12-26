package com.edu.book.study.aspect

import org.springframework.aop.ThrowsAdvice

/**
 * @Classname StudyThrowAdvice
 * @Description TODO
 * @Date 2024/12/25 17:02
 * @Created by liukaihua
 */
class StudyThrowAdvice: ThrowsAdvice {


    fun afterThrowing(exception: Exception) {
        println("异常信息：${exception.message}")
    }

}