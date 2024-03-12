package com.edu.book.application.task

import com.edu.book.application.client.WechatApi
import com.edu.book.infrastructure.config.SystemConfig
import com.edu.book.infrastructure.repositoryImpl.cache.repo.WechatCacheRepo
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

/**
 * @Auther: liukaihua
 * @Date: 2024/3/11 22:20
 * @Description:
 */

@Component
class UnsetWechatAccessTokenTask {

    private val logger = LoggerFactory.getLogger(UnsetWechatAccessTokenTask::class.java)

    @Autowired
    private lateinit var systemConfig: SystemConfig

    @Autowired
    private lateinit var wechatCacheRepo: WechatCacheRepo

    @Autowired
    private lateinit var wechatApi: WechatApi

    /**
     * 定时任务
     */
    @Scheduled(cron = "0 0 */1 * * ?")
    fun unsetWechatAccessToken() {
        //获取accessToken
        val accessToken = wechatApi.getAccessToken(systemConfig.wechatAppId, systemConfig.wechatAppSecret)
        logger.info("通过定时任务定时获取token，返回:${accessToken}")
        wechatCacheRepo.setAccessToken(systemConfig.wechatAppId, accessToken)
    }

}