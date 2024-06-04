package com.edu.book.api.http.service

import com.edu.book.api.http.common.CurrentHolder
import com.edu.book.api.vo.read.PublishReadCircleVo
import com.edu.book.application.service.BookReadCircleAppService
import com.edu.book.domain.read.dto.PublishReadCircleDto
import com.edu.book.infrastructure.util.MapperUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @Auther: liukaihua
 * @Date: 2024/6/4 19:34
 * @Description:
 */

@Service
class BookReadCircleWebService {

    @Autowired
    private lateinit var bookReadCircleAppService: BookReadCircleAppService

    /**
     * 发布阅读圈
     */
    fun publishReadCircle(vo: PublishReadCircleVo) {
        val dto = MapperUtil.map(PublishReadCircleDto::class.java, vo).apply {
            this.userUid = CurrentHolder.userDto!!.uid!!
        }
        bookReadCircleAppService.publishReadCircle(dto)
    }

}