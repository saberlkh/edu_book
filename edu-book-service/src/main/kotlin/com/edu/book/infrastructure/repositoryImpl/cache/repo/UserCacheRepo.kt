package com.edu.book.infrastructure.repositoryImpl.cache.repo

import com.edu.book.infrastructure.constants.RedisKeyConstant.USER_TOKEN_KEY
import java.util.concurrent.TimeUnit
import javax.annotation.Resource
import org.redisson.api.RedissonClient
import org.springframework.stereotype.Component

/**
 * @Auther: liukaihua
 * @Date: 2024/3/13 23:01
 * @Description:
 */

@Component
class UserCacheRepo {

    @Resource
    private lateinit var redissonClient: RedissonClient

    companion object {

        const val SEVEN = 7L

    }

    /**
     * 设置缓存
     */
    fun setUserToken(userUid: String, token: String) {
        val key = USER_TOKEN_KEY + userUid
        redissonClient.getBucket<String>(key).set(token, SEVEN, TimeUnit.DAYS)
    }

    /**
     * 删除token
     */
    fun removeUserToken(userUid: String) {
        val key = USER_TOKEN_KEY + userUid
        redissonClient.getBucket<String>(key).delete()
    }

    /**
     * 获取缓存
     */
    fun getUserToken(userUid: String): String {
        val key = USER_TOKEN_KEY + userUid
        return redissonClient.getBucket<String>(key).get()
    }

}