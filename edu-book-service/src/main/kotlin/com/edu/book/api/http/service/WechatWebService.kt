package com.edu.book.api.http.service

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.TypeReference
import com.edu.book.api.vo.wechat.WechatApiLoginRespVo
import com.edu.book.api.vo.wechat.WechatLoginRespVo
import com.edu.book.application.client.OkHttpClientManager
import com.edu.book.infrastructure.config.SystemConfig
import com.edu.book.infrastructure.constants.WechatConstant
import com.edu.book.infrastructure.enums.ErrorCodeConfig
import com.edu.book.infrastructure.exception.WebAppException
import org.apache.commons.lang3.ObjectUtils
import org.apache.commons.lang3.math.NumberUtils
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @Auther: liukaihua
 * @Date: 2024/3/11 19:04
 * @Description:
 */

@Service
class WechatWebService {

    private val logger = LoggerFactory.getLogger(WechatWebService::class.java)

    @Autowired
    private lateinit var systemConfig: SystemConfig

    @Autowired
    private lateinit var okHttpClientManager: OkHttpClientManager

    /**
     * 微信登录
     */
    fun wechatLogin(code: String): WechatLoginRespVo {
        //通过http接口调用微信登录
        val urlParamMap = mapOf(
            "appid" to systemConfig.wechatAppId,
            "secret" to systemConfig.wechatAppSecret,
            "js_code" to code,
            "grant_type" to WechatConstant.LOGIN_AUTHORIZATION_CODE
        )
        val httpResult = okHttpClientManager.get(systemConfig.wechatApiDomain, systemConfig.wechatApiLoginUrl, emptyMap(), urlParamMap, object : TypeReference<WechatApiLoginRespVo>() {})
        logger.info("调用微信登录http接口 返回 httpResult:${JSON.toJSONString(httpResult)}")
        if (httpResult == null || ObjectUtils.notEqual(httpResult.errcode, NumberUtils.INTEGER_ZERO)) throw WebAppException(ErrorCodeConfig.WECHAT_LOGIN_FAIL)
        return WechatLoginRespVo().apply {
            this.sessionKey = httpResult.session_key ?: ""
            this.openId = httpResult.openid ?: ""
            this.unionId = httpResult.unionid ?: ""
        }
    }

}