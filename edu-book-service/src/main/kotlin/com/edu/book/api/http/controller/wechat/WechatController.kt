package com.edu.book.api.http.controller.wechat

import com.edu.book.api.http.service.WechatWebService
import com.edu.book.api.vo.wechat.WechatGetAccessTokenVo
import com.edu.book.api.vo.wechat.WechatGetPhoneVo
import com.edu.book.api.vo.wechat.WechatLoginRespVo
import com.edu.book.infrastructure.anno.Response
import com.edu.book.infrastructure.response.ResponseVo
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * @Auther: liukaihua
 * @Date: 2024/3/11 19:02
 * @Description:
 */

@RestController
@RequestMapping("/wechat")
@Response
class WechatController {

    private val logger = LoggerFactory.getLogger(WechatController::class.java)

    @Autowired
    private lateinit var wechatWebService: WechatWebService

    /**
     * 微信登录
     */
    @GetMapping("/v1/login")
    fun wechatLogin(@RequestParam code: String): ResponseVo<WechatLoginRespVo> {
        return ResponseVo(wechatWebService.wechatLogin(code))
    }

    /**
     * 获取token
     */
    @GetMapping("/v1/access_token")
    fun getWechatAccessToken(): ResponseVo<WechatGetAccessTokenVo> {
        return ResponseVo(WechatGetAccessTokenVo().apply { this.accessToken = wechatWebService.getWechatAccessToken() })
    }

    /**
     * 获取手机号
     */
    @GetMapping("/v1/phone")
    fun getWechatPhone(@RequestParam accessToken: String, @RequestParam code: String): ResponseVo<WechatGetPhoneVo> {
        return ResponseVo(wechatWebService.getWechatPhone(accessToken, code))
    }

}