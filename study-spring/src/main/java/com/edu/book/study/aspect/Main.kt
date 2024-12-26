package com.edu.book.study.aspect

import com.edu.book.study.config.StudyConfig
import org.springframework.context.annotation.AnnotationConfigApplicationContext

/**
 * @Classname Main
 * @Description TODO
 * @Date 2024/12/25 17:04
 * @Created by liukaihua
 */

class Main {

}


fun main() {

//    val target = UserService()
//    val proxy = ProxyFactory()
//    proxy.setTarget(target)
//    proxy.addAdvice(StudyThrowAdvice())
//    val userProxy = proxy.getProxy() as UserService
//    userProxy.test()

    val context = AnnotationConfigApplicationContext(StudyConfig::class.java)
    val userService = context.getBean("userService", UserService::class.java)
    userService.test("1231", "asfafas")

}