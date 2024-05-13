package com.edu.book.api.http.controller.user

import com.edu.book.api.http.service.UserWebService
import com.edu.book.api.vo.user.BindAccountRespVo
import com.edu.book.api.vo.user.BindAccountVo
import com.edu.book.api.vo.user.CreateAccountRespVo
import com.edu.book.api.vo.user.LoginOrRegisterVo
import com.edu.book.api.vo.user.PageQueryAccountParamVo
import com.edu.book.api.vo.user.PageQueryAccountVo
import com.edu.book.api.vo.user.RegisterUserVo
import com.edu.book.api.vo.user.UnbindAccountRespVo
import com.edu.book.api.vo.user.UnbindAccountVo
import com.edu.book.infrastructure.anno.Response
import com.edu.book.infrastructure.response.ResponseVo
import com.edu.book.infrastructure.util.page.Page
import javax.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

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
     * 生成账号
     */
    @PostMapping("/v1/account")
    fun uploadFileCreateAccount(file: MultipartFile, classUid: String): CreateAccountRespVo {
        return userWebService.uploadFileCreateAccount(file, classUid)
    }

    /**
     * 分页查询
     */
    @GetMapping("/v1/account/page")
    fun pageQueryAccountListByClass(@Valid vo: PageQueryAccountParamVo): Page<PageQueryAccountVo> {
        return userWebService.pageQueryAccountListByClass(vo)
    }

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

    /**
     * 解绑
     */
    @PostMapping("/v1/unbind")
    fun unbindAccount(@RequestBody vo: UnbindAccountVo): ResponseVo<UnbindAccountRespVo> {
        return ResponseVo(userWebService.userUnbindAccount(vo))
    }

}