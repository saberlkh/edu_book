package com.edu.book.infrastructure.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

/**
 * @Auther: liukaihua
 * @Date: 2024/3/11 19:05
 * @Description:
 */

@Configuration
class SystemConfig {

    /**
     * 微信appid
     */
    @Value("\${wechat.appid}")
    var wechatAppId: String = ""

    /**
     * 微信密钥
     */
    @Value("\${wechat.appsecret}")
    var wechatAppSecret: String = ""

    /**
     * 微信登录api
     */
    @Value("\${wechat.api.domain}")
    var wechatApiDomain: String = ""

    /**
     * 微信登录api
     */
    @Value("\${wechat.api.login.url}")
    var wechatApiLoginUrl: String = ""

    /**
     * 微信登录api
     */
    @Value("\${wechat.api.get.token.url}")
    var wechatApiGetTokenUrl: String = ""

}