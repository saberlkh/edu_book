package com.edu.book.application.service

import com.edu.book.application.client.IsbnApi
import com.edu.book.domain.book.dto.AddBookMenuDto
import com.edu.book.domain.book.dto.BookDetailDto
import com.edu.book.domain.book.dto.BorrowBookDto
import com.edu.book.domain.book.dto.CollectBookDto
import com.edu.book.domain.book.dto.DeleteBookMenuDto
import com.edu.book.domain.book.dto.ModifyBookDetailDto
import com.edu.book.domain.book.dto.ModifyBookMenuDto
import com.edu.book.domain.book.dto.PageQueryBookCollectDto
import com.edu.book.domain.book.dto.PageQueryBookDto
import com.edu.book.domain.book.dto.PageQueryBookIsbnDto
import com.edu.book.domain.book.dto.PageQueryBookIsbnResultEntity
import com.edu.book.domain.book.dto.PageQueryBookResultDto
import com.edu.book.domain.book.dto.PageQueryBorrowBookDto
import com.edu.book.domain.book.dto.PageQueryBorrowBookResultDto
import com.edu.book.domain.book.dto.PageQueryUserBookCollectParam
import com.edu.book.domain.book.dto.QueryBookMenuResultDto
import com.edu.book.domain.book.dto.ReturnBookDto
import com.edu.book.domain.book.dto.ScanBookCodeInStorageParam
import com.edu.book.domain.book.dto.ScanIsbnCodeBookDto
import com.edu.book.domain.book.exception.QueryIsbnApiInfoErrorException
import com.edu.book.domain.book.mapper.BookEntityMapper.buildBookDto
import com.edu.book.domain.book.mapper.BookEntityMapper.buildScanIsbnCodeBookDto
import com.edu.book.domain.book.service.BookDomainService
import com.edu.book.domain.user.exception.ConcurrentCreateInteractRoomException
import com.edu.book.infrastructure.config.SystemConfig
import com.edu.book.infrastructure.constants.RedisKeyConstant.SCAN_ISBN_LOCK_KEY
import com.edu.book.infrastructure.util.page.Page
import java.util.concurrent.TimeUnit
import javax.annotation.Resource
import org.apache.commons.lang3.ObjectUtils
import org.apache.commons.lang3.math.NumberUtils
import org.redisson.api.RedissonClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @Auther: liukaihua
 * @Date: 2024/3/25 09:22
 * @Description:
 */

@Service
class BookAppService {

    @Autowired
    private lateinit var bookDomainService: BookDomainService

    @Autowired
    private lateinit var isbnApi: IsbnApi

    @Resource
    private lateinit var redissonClient: RedissonClient

    @Autowired
    private lateinit var systemConfig: SystemConfig

    /**
     * 分页查询
     */
    fun pageQueryBorrowFlow(dto: PageQueryBorrowBookDto): Page<PageQueryBorrowBookResultDto> {
        return bookDomainService.pageQueryBorrowFlow(dto)
    }

    /**
     * 分页查询收藏列表
     */
    fun pageQueryCollectList(param: PageQueryUserBookCollectParam): Page<PageQueryBookCollectDto> {
        return bookDomainService.pageQueryCollectList(param)
    }

    /**
     * 收藏
     */
    fun collectBook(dto: CollectBookDto) {
        bookDomainService.collectBook(dto)
    }

    /**
     * 还书
     */
    fun returnBook(dto: ReturnBookDto) {
        return bookDomainService.returnBook(dto)
    }

    /**
     * 添加书单
     */
    fun addBookMenu(dto: AddBookMenuDto) {
        bookDomainService.addBookMenu(dto)
    }

    /**
     * 更新书单
     */
    fun modifyBookMenu(dto: ModifyBookMenuDto) {
        bookDomainService.modifyBookMenu(dto)
    }

    /**
     * 查询书单列表
     */
    fun getBookMenus(gardenUid: String?, kindergartenUid: String?): List<QueryBookMenuResultDto> {
        return bookDomainService.getBookMenus(gardenUid, kindergartenUid)
    }

    /**
     * 借书
     */
    fun borrowBook(dto: BorrowBookDto) {
        bookDomainService.borrowBook(dto)
    }

    /**
     * 图书扫码入库
     */
    fun scanBookCodeInStorage(dto: ScanBookCodeInStorageParam) {
        bookDomainService.scanBookCodeInStorage(dto)
    }

    /**
     * 更新图书详情
     */
    fun modifyBookDetail(dto: ModifyBookDetailDto) {
        bookDomainService.modifyBookDetail(dto)
    }

    /**
     * 查询图书详情
     */
    fun findBookDetail(bookUid: String, userUid: String?): BookDetailDto {
        return bookDomainService.findBookDetail(bookUid, userUid)
    }

    /**
     * 删除图书
     */
    fun deleteBookDetail(bookUid: String) {
        bookDomainService.deleteBookDetail(bookUid)
    }

    /**
     * 扫码isbn
     */
    fun scanIsbnCode(isbnCode: String): ScanIsbnCodeBookDto {
        val lockKey = SCAN_ISBN_LOCK_KEY + isbnCode
        val lock = redissonClient.getLock(lockKey)
        try {
            if (!lock.tryLock(systemConfig.distributedLockWaitTime, systemConfig.distributedLockReleaseTime, TimeUnit.MILLISECONDS)) {
                throw ConcurrentCreateInteractRoomException(isbnCode)
            }
            //通过isbn查询数据库
            val dbBookDto = bookDomainService.findBookByIsbnCode(isbnCode)
            if (dbBookDto != null) {
                return buildScanIsbnCodeBookDto(dbBookDto)
            }
            //通过isbn的api进行查询
            val isbnApiResult = isbnApi.getBookInfoByIsbnCode(isbnCode)
            if (isbnApiResult == null || ObjectUtils.notEqual(isbnApiResult.status, NumberUtils.INTEGER_ZERO.toString()) || isbnApiResult.result == null) {
                throw QueryIsbnApiInfoErrorException()
            }
            //数据插入
            val addBookDto = buildBookDto(isbnApiResult.result!!)
            bookDomainService.saveIsbnBookInfo(addBookDto)
            //数据返回
            return buildScanIsbnCodeBookDto(addBookDto)
        } finally {
            if (lock.isHeldByCurrentThread) {
                lock.unlock()
            }
        }
    }

    /**
     * 分页查询
     */
    fun pageQueryBooks(dto: PageQueryBookDto): Page<PageQueryBookResultDto> {
        return bookDomainService.pageQueryBooks(dto)
    }

    /**
     * 查询isbn列表
     */
    fun getIsbnList(dto: PageQueryBookIsbnDto, isbn: String?): Page<PageQueryBookIsbnResultEntity> {
        return bookDomainService.getIsbnList(dto, isbn)
    }

}