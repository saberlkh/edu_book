package com.study.service

import com.spring.Autowired
import com.spring.Component
import com.spring.InitializingBean
import com.spring.Scope

@Component("userService")
@Scope("singleton")
class UserService: InitializingBean {

    @Autowired
    private lateinit var orderService: OrderService

    fun test() {
        println("test")
        println(orderService)
    }

    override fun afterPropertiesSet() {
        println("初始化")
    }

}