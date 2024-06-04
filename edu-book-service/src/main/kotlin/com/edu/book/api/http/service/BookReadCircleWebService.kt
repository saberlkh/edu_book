package com.edu.book.api.http.service

import com.edu.book.api.http.common.CurrentHolder
import com.edu.book.api.vo.read.PageQueryReadCircleParamVo
import com.edu.book.api.vo.read.PageReadCircleVo
import com.edu.book.api.vo.read.PublishReadCircleVo
import com.edu.book.application.service.BookReadCircleAppService
import com.edu.book.domain.read.dto.PageQueryReadCircleParam
import com.edu.book.domain.read.dto.PageReadCircleDto
import com.edu.book.domain.read.dto.PublishReadCircleDto
import com.edu.book.infrastructure.util.MapperUtil
import com.edu.book.infrastructure.util.page.Page
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

    /**
     * 分页查询阅读圈
     */
    fun pageQueryReadCircle(vo: PageQueryReadCircleParamVo): Page<PageReadCircleVo> {
        val param = MapperUtil.map(PageQueryReadCircleParam::class.java, vo)
        val pageQuery = bookReadCircleAppService.pageQueryReadCircle(param)
        if (pageQuery.result.isNullOrEmpty()) return Page()
        return Page(param.page, param.pageSize, pageQuery.totalCount, MapperUtil.mapToList(PageReadCircleVo::class.java, pageQuery.result!!))
    }

    /**
     * 查询详情
     */
    fun getReadCircleDetail(circleUid: String): PageReadCircleVo {
        val dto = bookReadCircleAppService.getReadCircleDetail(circleUid)
        return MapperUtil.map(PageReadCircleVo::class.java, dto)
    }

}