package com.edu.book.api.http.controller.user

import com.edu.book.api.http.service.UserWebService
import com.edu.book.api.vo.user.BindAccountRespVo
import com.edu.book.api.vo.user.BindAccountVo
import com.edu.book.api.vo.user.LoginOrRegisterVo
import com.edu.book.api.vo.user.RegisterUserVo
import com.edu.book.infrastructure.anno.Response
import com.edu.book.infrastructure.response.ResponseVo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @Auther: liukaihua
 * @Date: 2024/3/13 23:53
 * @Description:
 */

@RestController
@RequestMapping("/user")
@Response
class UserController {

    @Autowired
    private lateinit var userWebService: UserWebService

    /**
     * 登录
     */
    @PostMapping("/v1/login")
    fun registerUser(@RequestBody vo: LoginOrRegisterVo): ResponseVo<RegisterUserVo> {
        return ResponseVo(userWebService.registerUser(vo.openId))
    }

    /**
     * 绑定
     */
    @PostMapping("/v1/bind")
    fun bindAccount(@RequestBody vo: BindAccountVo): ResponseVo<BindAccountRespVo> {
        return ResponseVo(userWebService.userBindAccount(vo))
    }

}