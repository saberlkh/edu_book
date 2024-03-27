package com.edu.book.api.http.service

import com.edu.book.api.vo.book.BookDetailVo
import com.edu.book.api.vo.book.PageQueryBookResultVo
import com.edu.book.api.vo.book.PageQueryBookVo
import com.edu.book.api.vo.book.ScanBookCodeInStorageVo
import com.edu.book.api.vo.book.ScanIsbnCodeBookVo
import com.edu.book.application.service.BookAppService
import com.edu.book.domain.book.dto.PageQueryBookDto
import com.edu.book.domain.book.dto.PageQueryBookResultDto
import com.edu.book.domain.book.dto.ScanBookCodeInStorageDto
import com.edu.book.infrastructure.util.MapperUtil
import com.edu.book.infrastructure.util.page.Page
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @Auther: liukaihua
 * @Date: 2024/3/25 10:29
 * @Description:
 */

@Service
class BookWebService {

    @Autowired
    private lateinit var bookAppService: BookAppService

    /**
     * 图书扫码入库
     */
    fun scanBookCodeInStorage(vo: ScanBookCodeInStorageVo) {
        val dto = MapperUtil.map(ScanBookCodeInStorageDto::class.java, vo)
        bookAppService.scanBookCodeInStorage(dto)
    }

    /**
     * 查询图书详情
     */
    fun findBookDetail(bookUid: String): BookDetailVo {
        val dto = bookAppService.findBookDetail(bookUid)
        return MapperUtil.map(BookDetailVo::class.java, dto)
    }

    /**
     * 扫码
     */
    fun scanIsbnCode(isbnCode: String): ScanIsbnCodeBookVo {
        val dto = bookAppService.scanIsbnCode(isbnCode)
        return MapperUtil.map(ScanIsbnCodeBookVo::class.java, dto).apply {
            this.`class` = dto.`class`
        }
    }

    /**
     * 分页查询
     */
    fun pageQueryBooks(vo: PageQueryBookVo): Page<PageQueryBookResultVo> {
        val paramDto = MapperUtil.map(PageQueryBookDto::class.java, vo)
        val pageResult = bookAppService.pageQueryBooks(paramDto)
        if (pageResult.result.isNullOrEmpty()) return Page()
        return Page(vo.page, vo.pageSize, pageResult.totalCount, MapperUtil.mapToList(PageQueryBookResultVo::class.java, pageResult.result!!))
    }

}