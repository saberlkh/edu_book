package com.edu.book.application.service

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.TypeReference
import com.edu.book.api.vo.isbn.GetBookInfoByIsbnRespDto
import com.edu.book.application.client.IsbnApi
import com.edu.book.application.client.OkHttpClientManager
import com.edu.book.infrastructure.config.SystemConfig
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @Auther: liukaihua
 * @Date: 2024/3/24 23:00
 * @Description:
 */

@Service
class IsbnApiImpl: IsbnApi {

    private val logger = LoggerFactory.getLogger(IsbnApiImpl::class.java)

    @Autowired
    private lateinit var systemConfig: SystemConfig

    @Autowired
    private lateinit var okHttpClientManager: OkHttpClientManager

    /**
     * 获取详情
     */
    override fun getBookInfoByIsbnCode(isbnCode: String): GetBookInfoByIsbnRespDto? {
        val headerMap = mapOf(
            "Content-Type" to "application/json; charset=UTF-8",
            "Authorization" to "APPCODE " + systemConfig.isbnAppCode
        )
        val urlMap = mapOf(
            "isbn" to isbnCode
        )
        val result = okHttpClientManager.get(systemConfig.isbnHost, systemConfig.isbnQueryApiUrl, headerMap, urlMap, object: TypeReference<GetBookInfoByIsbnRespDto>() {})
        logger.info("通过isbn查询图书详情，返回result:${JSON.toJSONString(result)}")
        return result
    }

}