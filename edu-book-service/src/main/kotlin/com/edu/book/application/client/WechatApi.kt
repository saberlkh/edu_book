package com.edu.book.application.client

import com.edu.book.api.vo.wechat.WechatApiLoginRespVo
import com.edu.book.infrastructure.dto.wechat.WechatGetPhoneApiDto

/**
 * @Auther: liukaihua
 * @Date: 2024/3/11 22:29
 * @Description:
 */
interface WechatApi {

    /**
     * 获取accessToken
     */
    fun getAccessToken(appId: String, appSecret: String): String

    /**
     * 登录
     */
    fun wechatLogin(code: String): WechatApiLoginRespVo?

    /**
     * 获取手机号
     */
    fun getPhone(accessToken: String, code: String): WechatGetPhoneApiDto?

}