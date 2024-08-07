package com.edu.book.api.http.controller.book

import com.edu.book.api.http.service.BookWebService
import com.edu.book.api.vo.book.BookAgeVo
import com.edu.book.api.vo.book.BookClassifyVo
import com.edu.book.api.vo.book.BookCollectVo
import com.edu.book.api.vo.book.BookDetailVo
import com.edu.book.api.vo.book.BorrowBookVo
import com.edu.book.api.vo.book.ModifyBookDetailVo
import com.edu.book.api.vo.book.PageQueryBookCollectVo
import com.edu.book.api.vo.book.PageQueryBookResultVo
import com.edu.book.api.vo.book.PageQueryBookVo
import com.edu.book.api.vo.book.PageQueryBorrowBookResultVo
import com.edu.book.api.vo.book.PageQueryBorrowBookVo
import com.edu.book.api.vo.book.PageQueryUserBookCollectParamVo
import com.edu.book.api.vo.book.ReturnBookVo
import com.edu.book.api.vo.book.ScanBookCodeInStorageVo
import com.edu.book.api.vo.book.ScanIsbnCodeBookVo
import com.edu.book.domain.book.dto.AddBookMenuDto
import com.edu.book.domain.book.dto.DeleteBookMenuDto
import com.edu.book.domain.book.dto.ModifyBookMenuDto
import com.edu.book.domain.book.dto.PageQueryBookIsbnDto
import com.edu.book.domain.book.dto.PageQueryBookIsbnResultEntity
import com.edu.book.domain.book.dto.QueryBookMenuResultDto
import com.edu.book.infrastructure.anno.Response
import com.edu.book.infrastructure.enums.ErrorCodeConfig
import com.edu.book.infrastructure.exception.WebAppException
import com.edu.book.infrastructure.util.page.Page
import javax.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
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
     * 还书
     */
    @PostMapping("/v1/return")
    fun returnBook(@RequestBody @Valid vo: ReturnBookVo) {
        bookWebService.returnBook(vo)
    }

    /**
     * 添加书单
     */
    @PostMapping("/v1/menu")
    fun addBookMenu(@RequestBody @Valid dto: AddBookMenuDto) {
        bookWebService.addBookMenu(dto)
    }

    /**
     * 删除书单
     */
    @DeleteMapping("/v1/menu")
    fun deleteBookMenu(@RequestBody @Valid dto: DeleteBookMenuDto) {
        bookWebService.deleteBookMenu(dto.bookMenuUid)
    }

    /**
     * 更新书单
     */
    @PutMapping("/v1/menu")
    fun modifyBookMenu(@RequestBody @Valid dto: ModifyBookMenuDto) {
        bookWebService.modifyBookMenu(dto)
    }

    /**
     * 获取书单信息
     */
    @GetMapping("/v1/menu")
    fun getBookMenu(@RequestParam(required = false) gardenUid: String?, @RequestParam(required = false) kindergartenUid: String?): List<QueryBookMenuResultDto> {
        return bookWebService.getBookMenus(gardenUid, kindergartenUid)
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
     * 修改图书
     */
    @PutMapping("/v1/modify")
    fun modifyBookDetail(@RequestBody @Valid vo: ModifyBookDetailVo) {
        bookWebService.modifyBookDetail(vo)
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
     * 查询已借列表
     */
    @GetMapping("/v1/borrow/page")
    fun pageQueryBorrowFlow(vo: PageQueryBorrowBookVo): Page<PageQueryBorrowBookResultVo> {
        if (vo.gardenUid.isNullOrBlank()) throw WebAppException(ErrorCodeConfig.GARDEN_UID_NOT_NULL)
        return bookWebService.pageQueryBorrowFlow(vo)
    }

    /**
     * 查询收藏
     */
    @GetMapping("/v1/collect/page")
    fun pageQueryCollectList(vo: PageQueryUserBookCollectParamVo): Page<PageQueryBookCollectVo> {
        return bookWebService.pageQueryCollectList(vo)
    }

    /**
     * 收藏
     */
    @PostMapping("/v1/collect")
    fun collectBook(@RequestBody @Valid vo: BookCollectVo) {
        bookWebService.collectBook(vo)
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
    fun getIsbnList(dto: PageQueryBookIsbnDto): Page<PageQueryBookIsbnResultEntity> {
        return bookWebService.getIsbnList(dto, null)
    }

}