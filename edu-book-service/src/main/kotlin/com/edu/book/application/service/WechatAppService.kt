package com.edu.book.application.service

import com.edu.book.domain.wechat.WechatDomainService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @Auther: liukaihua
 * @Date: 2024/3/11 22:41
 * @Description:
 */

@Service
class WechatAppService {

    @Autowired
    private lateinit var wechatDomainService: WechatDomainService

    /**
     * 获取accessToken
     */
    fun getWechatAccessToken(appId: String): String {
        return wechatDomainService.getWechatAccessTokenCache(appId)
    }

    /**
     * 设置
     */
    fun setWechatAccessTokenCache(appId: String, accessToken: String) {
        wechatDomainService.setWechatAccessTokenCache(appId, accessToken)
    }

}