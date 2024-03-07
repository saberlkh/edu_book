package com.edu.book.api.http.controller

import com.edu.book.infrastructure.anno.Response
import com.edu.book.infrastructure.response.ResponseVo
import javax.annotation.Resource
import org.redisson.api.RedissonClient
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @Auther: liukaihua
 * @Date: 2024/3/7 10:44
 * @Description:
 */

@RestController
@RequestMapping("/test")
@Response
class TestController {

    @Resource
    private lateinit var redissonClient: RedissonClient

    private val logger = LoggerFactory.getLogger(TestController::class.java)

    @PostMapping("/setRedis")
    fun setRedis(): ResponseVo<String> {
        redissonClient.getBucket<String>("testKet").set("agsthklaa")
        logger.info("日志打印==============")
        return ResponseVo(redissonClient.getBucket<String>("testKet").get())
    }

}