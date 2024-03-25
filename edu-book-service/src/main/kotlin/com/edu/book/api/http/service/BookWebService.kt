package com.edu.book.api.http.service

import com.edu.book.api.vo.book.ScanBookCodeInStorageVo
import com.edu.book.api.vo.book.ScanIsbnCodeBookVo
import com.edu.book.application.service.BookAppService
import com.edu.book.domain.book.dto.ScanBookCodeInStorageDto
import com.edu.book.infrastructure.util.MapperUtil
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
     * 扫码
     */
    fun scanIsbnCode(isbnCode: String): ScanIsbnCodeBookVo {
        val dto = bookAppService.scanIsbnCode(isbnCode)
        return MapperUtil.map(ScanIsbnCodeBookVo::class.java, dto).apply {
            this.`class` = dto.`class`
        }
    }

}