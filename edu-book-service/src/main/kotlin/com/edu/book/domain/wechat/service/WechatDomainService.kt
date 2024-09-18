package com.edu.book.domain.wechat.service

import com.edu.book.infrastructure.repositoryImpl.cache.repo.WechatCacheRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @Auther: liukaihua
 * @Date: 2024/3/11 22:40
 * @Description:
 */

@Service
class WechatDomainService {

    @Autowired
    private lateinit var wechatCacheRepo: WechatCacheRepo

    /**
     * 获取token
     */
    fun getWechatAccessTokenCache(appId: String): String {
        return wechatCacheRepo.getAccessToken(appId)
    }

    /**
     * 设置
     */
    fun setWechatAccessTokenCache(appId: String, accessToken: String) {
        wechatCacheRepo.setAccessToken(appId, accessToken)
    }

}