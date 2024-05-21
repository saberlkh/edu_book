package com.edu.book.api.http.controller.book

import com.edu.book.api.http.service.BookWebService
import com.edu.book.api.vo.book.BookAgeVo
import com.edu.book.api.vo.book.BookClassifyVo
import com.edu.book.api.vo.book.BookDetailVo
import com.edu.book.api.vo.book.BorrowBookVo
import com.edu.book.api.vo.book.PageQueryBookResultVo
import com.edu.book.api.vo.book.PageQueryBookVo
import com.edu.book.api.vo.book.ScanBookCodeInStorageVo
import com.edu.book.api.vo.book.ScanIsbnCodeBookVo
import com.edu.book.domain.book.dto.BorrowBookDto
import com.edu.book.infrastructure.anno.Response
import com.edu.book.infrastructure.util.MapperUtil
import com.edu.book.infrastructure.util.page.Page
import javax.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
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
     * 借书
     */
    @PostMapping("/v1/borrow")
    fun borrowBook(@RequestBody @Valid vo: BorrowBookVo) {
        bookWebService.borrowBook(vo)
    }

    /**
     * 查询分类
     */
    @GetMapping("/v1/classify")
    fun queryBookClassifyByGarden(): List<BookClassifyVo> {
        return bookWebService.queryBookClassifyByGarden()
    }

    /**
     * 查询年龄段
     */
    @GetMapping("/v1/age")
    fun queryBookAgeGroup(): List<BookAgeVo> {
        return bookWebService.queryBookAgeGroup()
    }

    /**
     * 扫码
     */
    @PostMapping("/v1/{isbn}/scan")
    fun scanIsbnCode(@PathVariable isbn: String): ScanIsbnCodeBookVo {
        return bookWebService.scanIsbnCode(isbn)
    }

    /**
     * 扫码
     */
    @PostMapping("/v1/storage")
    fun scanBookCodeInStorage(@RequestBody @Valid vo: ScanBookCodeInStorageVo) {
        bookWebService.scanBookCodeInStorage(vo)
    }

    /**
     * 查询图书详情
     */
    @GetMapping("/v1/{bookUid}/detail")
    fun findBookDetail(@PathVariable bookUid: String): BookDetailVo {
        return bookWebService.findBookDetail(bookUid)
    }

    /**
     * 查询图书详情
     */
    @PostMapping("/v1/page")
    fun pageQuery(@RequestBody vo: PageQueryBookVo): Page<PageQueryBookResultVo> {
        return bookWebService.pageQueryBooks(vo)
    }

    /**
     * 删除图书
     */
    @DeleteMapping("/v1/{bookUid}")
    fun deleteBookDetail(@PathVariable bookUid: String) {
        bookWebService.deleteBookDetail(bookUid)
    }

    /**
     * 获取isbn列表
     */
    @GetMapping("/v1/isbn")
    fun getIsbnList(@RequestParam(required = false) garden: String?, @RequestParam(required = false) isbn: String?): List<String> {
        return bookWebService.getIsbnList(garden, isbn)
    }

}