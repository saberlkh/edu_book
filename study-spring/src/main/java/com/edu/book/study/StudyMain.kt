package com.edu.book.study

import com.edu.book.study.config.StudyConfig
import com.edu.book.study.mybatis.ZhouyuFactoryBean
import org.springframework.beans.factory.support.BeanDefinitionBuilder
import org.springframework.context.annotation.AnnotationConfigApplicationContext

/**
 * @Classname StudyMain
 * @Description TODO
 * @Date 2024/12/24 20:56
 * @Created by liukaihua
 */
class StudyMain {

}

fun main() {

    val context = AnnotationConfigApplicationContext()
    context.register(StudyConfig::class.java)
    val beanDefinition = BeanDefinitionBuilder.genericBeanDefinition().beanDefinition
    beanDefinition.beanClass = ZhouyuFactoryBean::class.java
//    beanDefinition.constructorArgumentValues.addGenericArgumentValue(UserMapper::class.java)
//    context.registerBeanDefinition("userMapper", beanDefinition)
    context.refresh()
//    val userService = context.getBean("userService", UserService::class.java)
//    userService.test()

}