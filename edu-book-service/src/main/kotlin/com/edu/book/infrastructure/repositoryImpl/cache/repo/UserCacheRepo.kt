package com.edu.book.infrastructure.repositoryImpl.cache.repo

import com.edu.book.domain.user.dto.UserTokenCacheDto
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
        val key = USER_TOKEN_KEY + token
        val dto = UserTokenCacheDto().apply {
            this.token = token
            this.userUid = userUid
        }
        redissonClient.getBucket<UserTokenCacheDto>(key).set(dto, SEVEN, TimeUnit.DAYS)
    }

    /**
     * 删除token
     */
    fun removeUserToken(token: String) {
        val key = USER_TOKEN_KEY + token
        redissonClient.getBucket<UserTokenCacheDto>(key).delete()
    }

    /**
     * 获取缓存
     */
    fun getUserToken(token: String): UserTokenCacheDto? {
        val key = USER_TOKEN_KEY + token
        return redissonClient.getBucket<UserTokenCacheDto>(key).get()
    }

}