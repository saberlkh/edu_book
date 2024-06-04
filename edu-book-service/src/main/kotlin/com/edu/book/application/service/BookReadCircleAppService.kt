package com.edu.book.application.service

import com.edu.book.domain.read.dto.PublishReadCircleDto
import com.edu.book.domain.read.service.BookReadCircleDomainService
import java.io.Serializable
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @Auther: liukaihua
 * @Date: 2024/6/3 23:28
 * @Description:
 */

@Service
class BookReadCircleAppService: Serializable {

    private val logger = LoggerFactory.getLogger(BookReadCircleAppService::class.java)

    @Autowired
    private lateinit var bookReadCircleDomainService: BookReadCircleDomainService

    /**
     * 发布阅读圈
     */
    fun publishReadCircle(dto: PublishReadCircleDto) {
        bookReadCircleDomainService.publishReadCircle(dto)
    }

}