//package com.edu.book.study.mybatis
//
//import com.edu.book.study.mapper.UserMapper
//import com.edu.book.study.service.UserService
//import org.springframework.beans.factory.FactoryBean
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.cglib.proxy.InvocationHandler
//import org.springframework.cglib.proxy.Proxy
//import org.springframework.stereotype.Component
//
///**
// * @Classname StudyKtFactoryBean
// * @Description TODO
// * @Date 2024/12/24 20:48
// * @Created by liukaihua
// */
//
//
//@Component
//class StudyFactorBean : FactoryBean<Any> {
//
//    private lateinit var mapperInterface: Class<Any?>
////
////    @Autowired
////    private lateinit var userMapper: UserMapper
//
////    @Autowired
////    private lateinit var userService: UserService
//
//    @Autowired
//    constructor(mapperInterface: Class<Any?>) {
//        this.mapperInterface = mapperInterface
//    }
//
//    @Throws(Exception::class)
//    override fun getObject(): Any? {
//        val proxyInstance = Proxy.newProxyInstance(
//            StudyFactorBean::class.java.classLoader, arrayOf(mapperInterface),
//            InvocationHandler { proxy, method, args ->
//                println(method?.name)
//                null
//            })
//        return proxyInstance
//    }
//
//    override fun getObjectType(): Class<*>? {
//        return mapperInterface
//    }
//}