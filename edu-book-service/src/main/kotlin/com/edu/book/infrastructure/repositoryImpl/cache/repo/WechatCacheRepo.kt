package com.edu.book.infrastructure.repositoryImpl.cache.repo

import com.edu.book.infrastructure.constants.RedisKeyConstant
import java.util.concurrent.TimeUnit
import javax.annotation.Resource
import org.apache.commons.lang3.math.NumberUtils
import org.redisson.api.RedissonClient
import org.springframework.stereotype.Component

/**
 * @Auther: liukaihua
 * @Date: 2024/3/11 22:24
 * @Description:
 */

@Component
class WechatCacheRepo {

    @Resource
    private lateinit var redissonClient: RedissonClient

    /**
     * 设置缓存
     */
    fun setAccessToken(appId: String, accessToken: String) {
        val key = RedisKeyConstant.WECHAT_ACCESS_TOKEN_KEY + appId
        redissonClient.getBucket<String>(key).set(accessToken, NumberUtils.LONG_ONE, TimeUnit.HOURS)
    }

    /**
     * 获取缓存
     */
    fun getAccessToken(appId: String): String {
        val key = RedisKeyConstant.WECHAT_ACCESS_TOKEN_KEY + appId
        return redissonClient.getBucket<String>(key).get() ?: ""
    }

    /**
     * 删除缓存
     */
    fun removeAccessToken(appId: String) {
        val key = RedisKeyConstant.WECHAT_ACCESS_TOKEN_KEY + appId
        redissonClient.getBucket<String>(key).delete()
    }

}