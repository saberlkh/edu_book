package com.edu.book.api

import com.edu.book.EduBoolServiceApplication
import com.edu.book.application.client.WechatApi
import com.edu.book.infrastructure.config.SystemConfig
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

/**
 * @Auther: liukaihua
 * @Date: 2024/3/13 10:11
 * @Description:
 */

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [EduBoolServiceApplication::class])
class WechatApiTest {

    @Autowired
    private lateinit var wechatApi: WechatApi

    @Autowired
    private lateinit var systemConfig: SystemConfig

    @Test
    fun `获取accessToken`() {
        val token = wechatApi.getAccessToken(systemConfig.wechatAppId, systemConfig.wechatAppSecret)
        println(token)
        println(wechatApi.getPhone(token, "adwqeqwe123we"))
    }

}