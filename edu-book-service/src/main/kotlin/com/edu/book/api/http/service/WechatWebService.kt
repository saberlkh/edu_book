package com.edu.book.api.http.service

import com.edu.book.api.vo.wechat.WechatGetPhoneVo
import com.edu.book.api.vo.wechat.WechatLoginRespVo
import com.edu.book.application.client.WechatApi
import com.edu.book.application.service.WechatAppService
import com.edu.book.domain.wechat.dto.WechatCheckMessageDto
import com.edu.book.infrastructure.config.SystemConfig
import com.edu.book.infrastructure.dto.wechat.CheckMessageResultDto
import com.edu.book.infrastructure.dto.wechat.WechatCheckMessageHttpRespDto
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
    private lateinit var wechatAppService: WechatAppService

    @Autowired
    private lateinit var wechatApi: WechatApi

    /**
     * 微信登录
     */
    fun wechatLogin(code: String): WechatLoginRespVo {
        val httpResult = wechatApi.wechatLogin(code)
        if (httpResult == null || ObjectUtils.notEqual(httpResult.errcode, NumberUtils.INTEGER_ZERO)) throw WebAppException(ErrorCodeConfig.WECHAT_LOGIN_FAIL)
        return WechatLoginRespVo().apply {
            this.sessionKey = httpResult.session_key ?: ""
            this.openId = httpResult.openid ?: ""
            this.unionId = httpResult.unionid ?: ""
        }
    }

    /**
     * 微信校验
     */
    fun wechatCheckMessage(dto: WechatCheckMessageDto): WechatCheckMessageHttpRespDto {
        val httpResult = wechatApi.checkMessage(dto)
        if (httpResult == null || ObjectUtils.notEqual(httpResult.errcode, NumberUtils.INTEGER_ZERO)) throw WebAppException(ErrorCodeConfig.WECHAT_CHECK_FAIL)
        return WechatCheckMessageHttpRespDto().apply {
            this.detail = httpResult.detail
            this.result = httpResult.result
        }
    }

    /**
     * 获取微信token
     * 先获取缓存，如果缓存获取不到，则进行http获取
     */
    fun getWechatAccessToken(): String {
        val cacheToken = wechatAppService.getWechatAccessToken(systemConfig.wechatAppId)
        val finalAccessToken = if (cacheToken.isNullOrBlank()) {
            val accessToken = wechatApi.getAccessToken(systemConfig.wechatAppId, systemConfig.wechatAppSecret)
            wechatAppService.setWechatAccessTokenCache(systemConfig.wechatAppId, accessToken)
            accessToken
        } else {
            cacheToken
        }
        return finalAccessToken
    }

    /**
     * 获取手机号
     */
    fun getWechatPhone(accessToken: String, code: String): WechatGetPhoneVo {
        val wechatPhoneDto = wechatApi.getPhone(accessToken, code)
        if (wechatPhoneDto == null || ObjectUtils.notEqual(wechatPhoneDto.errcode, NumberUtils.INTEGER_ZERO)) throw WebAppException(ErrorCodeConfig.WECHAT_GET_PHONE_FAIL)
        return WechatGetPhoneVo().apply {
            this.phone = wechatPhoneDto.phone_info?.phoneNumber ?: ""
            this.countryCode = wechatPhoneDto.phone_info?.countryCode ?: ""
        }
    }

}