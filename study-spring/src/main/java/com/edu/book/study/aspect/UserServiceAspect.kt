//package com.edu.book.study.aspect
//
//import org.aspectj.lang.annotation.Aspect
//import org.aspectj.lang.annotation.Before
//
///**
// * @Classname UserServiceAspect
// * @Description TODO
// * @Date 2024/12/26 11:17
// * @Created by liukaihua
// */
//
//@Aspect
//class UserServiceAspect {
//
//    @Before("execution(public void com.edu.book.study.aspect.UserService.test(..)) && args(a,b)", argNames = "a, b")
//    fun before(a: String, b: String) {
//        println(a)
//        println(b)
//        println("before")
//    }
//
//}