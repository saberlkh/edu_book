package com.study.service

import com.spring.Autowired
import com.spring.Component
import com.spring.Scope

@Component("userService")
@Scope("singleton")
class UserService {

    @Autowired
    private lateinit var orderService: OrderService

    fun test() {
        println("test")
        println(orderService)
    }

}