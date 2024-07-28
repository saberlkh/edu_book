package com.edu.book.api.http.service

import com.edu.book.api.http.common.CurrentHolder
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
import com.edu.book.application.service.BookAppService
import com.edu.book.domain.book.dto.AddBookMenuDto
import com.edu.book.domain.book.dto.BorrowBookDto
import com.edu.book.domain.book.dto.CollectBookDto
import com.edu.book.domain.book.dto.DeleteBookMenuDto
import com.edu.book.domain.book.dto.ModifyBookDetailDto
import com.edu.book.domain.book.dto.PageQueryBookDto
import com.edu.book.domain.book.dto.PageQueryBookIsbnDto
import com.edu.book.domain.book.dto.PageQueryBookIsbnResultEntity
import com.edu.book.domain.book.dto.PageQueryBorrowBookDto
import com.edu.book.domain.book.dto.PageQueryUserBookCollectParam
import com.edu.book.domain.book.dto.QueryBookMenuResultDto
import com.edu.book.domain.book.dto.ReturnBookDto
import com.edu.book.domain.book.dto.ScanBookCodeInStorageParam
import com.edu.book.domain.book.enums.AgeGroupEnum
import com.edu.book.domain.book.enums.BookClassifyEnum
import com.edu.book.domain.book.enums.BookDetailStatusEnum
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
     * 借书
     */
    fun borrowBook(vo: BorrowBookVo) {
        val dto = MapperUtil.map(BorrowBookDto::class.java, vo)
        bookAppService.borrowBook(dto)
    }

    /**
     * 还书
     */
    fun returnBook(vo: ReturnBookVo) {
        val dto = MapperUtil.map(ReturnBookDto::class.java, vo).apply {
            this.userUid = CurrentHolder.userDto!!.uid!!
            this.bookStatus = if (vo.loss) {
                BookDetailStatusEnum.LOSS.status
            } else {
                BookDetailStatusEnum.IN_STORAGE.status
            }
        }
        return bookAppService.returnBook(dto)
    }

    /**
     * 添加书单
     */
    fun addBookMenu(dto: AddBookMenuDto) {
        bookAppService.addBookMenu(dto)
    }

    /**
     * 删除书单
     */
    fun deleteBookMenu(dto: DeleteBookMenuDto) {
        bookAppService.deleteBookMenu(dto)
    }

    /**
     * 查询书单列表
     */
    fun getBookMenus(): List<QueryBookMenuResultDto> {
        return bookAppService.getBookMenus()
    }

    /**
     * 查询年龄段
     */
    fun queryBookAgeGroup(): List<BookAgeVo> {
        return AgeGroupEnum.values().toList().map {
            BookAgeVo().apply {
                this.ageGroupCode = it.age
                this.ageGroupName = it.desc
            }
        }
    }

    /**
     * 查询分类
     */
    fun queryBookClassifyByGarden(): List<BookClassifyVo> {
        return BookClassifyEnum.values().toList().map {
            BookClassifyVo().apply {
                this.classifyName = it.desc
                this.classifyCode = it.code
            }
        }
    }

    /**
     * 图书扫码入库
     */
    fun scanBookCodeInStorage(vo: ScanBookCodeInStorageVo) {
        val dto = MapperUtil.map(ScanBookCodeInStorageParam::class.java, vo)
        bookAppService.scanBookCodeInStorage(dto)
    }

    /**
     * 更新图书详情
     */
    fun modifyBookDetail(vo: ModifyBookDetailVo) {
        val dto = MapperUtil.map(ModifyBookDetailDto::class.java, vo)
        bookAppService.modifyBookDetail(dto)
    }

    /**
     * 查询图书详情
     */
    fun findBookDetail(bookUid: String): BookDetailVo {
        val userUid = CurrentHolder.userDto?.uid
        val dto = bookAppService.findBookDetail(bookUid, userUid)
        return MapperUtil.map(BookDetailVo::class.java, dto)
    }

    /**
     * 删除图书
     */
    fun deleteBookDetail(bookUid: String) {
        bookAppService.deleteBookDetail(bookUid)
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
     * 收藏
     */
    fun collectBook(vo: BookCollectVo) {
        val dto = MapperUtil.map(CollectBookDto::class.java, vo).apply {
            this.userUid = CurrentHolder.userDto!!.uid!!
        }
        bookAppService.collectBook(dto)
    }

    /**
     * 分页查询收藏列表哦
     */
    fun pageQueryCollectList(vo: PageQueryUserBookCollectParamVo): Page<PageQueryBookCollectVo> {
        val param = MapperUtil.map(PageQueryUserBookCollectParam::class.java, vo).apply {
            this.userUid = if (vo.userUid.isNullOrBlank()) {
                CurrentHolder.userDto!!.uid!!
            } else {
                vo.userUid
            }
        }
        val pageResult = bookAppService.pageQueryCollectList(param)
        if (pageResult.result.isNullOrEmpty()) return Page()
        val finalResult = pageResult.result!!.map {
            MapperUtil.map(PageQueryBookCollectVo::class.java, it).apply {
                this.pic = it.picUrl
                this.subtitle = it.subTitle
            }
        }
        return Page(vo.page, vo.pageSize, pageResult.totalCount, finalResult)
    }

    /**
     * 分页查询
     */
    fun pageQueryBorrowFlow(vo: PageQueryBorrowBookVo): Page<PageQueryBorrowBookResultVo> {
        val paramDto = MapperUtil.map(PageQueryBorrowBookDto::class.java, vo)
        val pageResult = bookAppService.pageQueryBorrowFlow(paramDto)
        if (pageResult.result.isNullOrEmpty()) return Page()
        val finalResult = pageResult.result!!.map {
            MapperUtil.map(PageQueryBorrowBookResultVo::class.java, it).apply {
                this.pic = it.picUrl
                this.subtitle = it.subTitle
            }
        }
        return Page(vo.page, vo.pageSize, pageResult.totalCount, finalResult)
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

    /**
     * 查询isbn列表
     */
    fun getIsbnList(dto: PageQueryBookIsbnDto, isbn: String?): Page<PageQueryBookIsbnResultEntity> {
        val pageResult = bookAppService.getIsbnList(dto, isbn)
        if (pageResult.result.isNullOrEmpty()) return Page()
        return Page(dto.page, dto.pageSize, pageResult.totalCount, pageResult.result!!)
    }

}