package com.edu.book.application.service

import com.edu.book.domain.read.dto.PageQueryReadCircleParam
import com.edu.book.domain.read.dto.PageReadCircleDto
import com.edu.book.domain.read.dto.PublishReadCircleDto
import com.edu.book.domain.read.service.BookReadCircleDomainService
import com.edu.book.infrastructure.util.page.Page
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

    /**
     * 分页查询阅读圈
     */
    fun pageQueryReadCircle(param: PageQueryReadCircleParam): Page<PageReadCircleDto> {
        return bookReadCircleDomainService.pageQueryReadCircle(param)
    }

    /**
     * 查询详情
     */
    fun getReadCircleDetail(circleUid: String): PageReadCircleDto {
        return bookReadCircleDomainService.getReadCircleDetail(circleUid)
    }

}