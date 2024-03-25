package com.edu.book.application.service

import com.edu.book.application.client.IsbnApi
import com.edu.book.domain.book.dto.ScanIsbnCodeBookDto
import com.edu.book.domain.book.exception.QueryIsbnApiInfoErrorException
import com.edu.book.domain.book.mapper.BookEntityMapper.buildBookDto
import com.edu.book.domain.book.mapper.BookEntityMapper.buildScanIsbnCodeBookDto
import com.edu.book.domain.book.service.BookDomainService
import com.edu.book.domain.user.exception.ConcurrentCreateInteractRoomException
import com.edu.book.infrastructure.config.SystemConfig
import com.edu.book.infrastructure.constants.RedisKeyConstant.SCAN_ISBN_LOCK_KEY
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

}