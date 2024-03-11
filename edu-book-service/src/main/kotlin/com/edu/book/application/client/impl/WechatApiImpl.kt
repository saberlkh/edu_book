package com.edu.book.application.client.impl

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.TypeReference
import com.edu.book.api.vo.wechat.WechatApiLoginRespVo
import com.edu.book.application.client.OkHttpClientManager
import com.edu.book.application.client.WechatApi
import com.edu.book.infrastructure.config.SystemConfig
import com.edu.book.infrastructure.constants.WechatConstant
import com.edu.book.infrastructure.dto.wechat.WechatGetAccessTokenApiDto
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @Auther: liukaihua
 * @Date: 2024/3/11 22:29
 * @Description:
 */

@Service
class WechatApiImpl: WechatApi {

    private val logger = LoggerFactory.getLogger(WechatApiImpl::class.java)

    @Autowired
    private lateinit var systemConfig: SystemConfig

    @Autowired
    private lateinit var okHttpClientManager: OkHttpClientManager

    /**
     * 获取accessToken
     */
    override fun getAccessToken(appId: String, appSecret: String): String {
        //通过http接口调用微信登录
        val urlParamMap = mapOf(
            "appid" to systemConfig.wechatAppId,
            "secret" to systemConfig.wechatAppSecret,
            "grant_type" to WechatConstant.GET_ACCESS_TOKEN_CODE
        )
        val httpResult = okHttpClientManager.get(systemConfig.wechatApiDomain, systemConfig.wechatApiGetTokenUrl, emptyMap(), urlParamMap, object: TypeReference<WechatGetAccessTokenApiDto>() {})
        logger.info("调用微信获取accessToken 返回 httpResult:${JSON.toJSONString(httpResult)}")
        return httpResult?.access_token ?: ""
    }

    /**
     * 登录
     */
    override fun wechatLogin(code: String): WechatApiLoginRespVo? {
        //通过http接口调用微信登录
        val urlParamMap = mapOf(
            "appid" to systemConfig.wechatAppId,
            "secret" to systemConfig.wechatAppSecret,
            "js_code" to code,
            "grant_type" to WechatConstant.LOGIN_AUTHORIZATION_CODE
        )
        val httpResult = okHttpClientManager.get(systemConfig.wechatApiDomain, systemConfig.wechatApiLoginUrl, emptyMap(), urlParamMap, object: TypeReference<WechatApiLoginRespVo>() {})
        return httpResult
    }

}