package com.edu.book.api.http.config

import okhttp3.OkHttpClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

import java.util.concurrent.TimeUnit

/**
 * Author ： Martin
 * Date : 17/11/28
 * Description : OKHttp客户端配置
 * Version : 1.0
 */
@Configuration
class OkHttpConfiguration {

    @Value("\${http.client.read.timeout:30}")
    var readTimeOut: Int? = null

    @Value("\${http.client.connect.timeout:10}")
    var connectTimeOut: Int? = null

    @Value("\${http.client.write.timeout:10}")
    var writeTimeOut: Int? = null

    @Bean
    fun httpClient(): OkHttpClient {
        return OkHttpClient()
                .newBuilder()
                .connectTimeout(connectTimeOut!!.toLong(), TimeUnit.SECONDS)
                .writeTimeout(writeTimeOut!!.toLong(), TimeUnit.SECONDS)
                .readTimeout(readTimeOut!!.toLong(), TimeUnit.SECONDS)
                .build()
    }
}
