package com.edu.book.api.http.controller.book

import com.edu.book.api.http.service.BookWebService
import com.edu.book.api.vo.book.ScanIsbnCodeBookVo
import com.edu.book.infrastructure.anno.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @Auther: liukaihua
 * @Date: 2024/3/25 10:31
 * @Description:
 */

@RestController
@RequestMapping("/book")
@Response
class BookController {

    @Autowired
    private lateinit var bookWebService: BookWebService

    /**
     * 扫码
     */
    @PostMapping("/v1/{isbnCode}/scan")
    fun scanIsbnCode(@PathVariable isbnCode: String): ScanIsbnCodeBookVo {
        return bookWebService.scanIsbnCode(isbnCode)
    }

}