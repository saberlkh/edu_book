package com.edu.book.application.task

import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.edu.book.domain.book.dto.PageQueryBorrowBookDto
import com.edu.book.domain.book.enums.BookBorrowStatusEnum
import com.edu.book.domain.book.repository.BookBorrowFlowRepository
import com.edu.book.infrastructure.constants.Constants
import com.edu.book.infrastructure.po.book.BookBorrowFlowPo
import com.edu.book.infrastructure.util.DateUtil
import java.util.*
import org.apache.commons.lang3.math.NumberUtils
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

/**
 * @Auther: liukaihua
 * @Date: 2024/5/30 20:01
 * @Description: 查询借阅记录，判断临近过期的，修改状态为2  临近过期定义（剩余借阅时间小于等于3天）
 */

@Component
class BookBorrowStatusUnsetTask {

    private val logger = LoggerFactory.getLogger(BookBorrowStatusUnsetTask::class.java)

    companion object {

        const val page_size = 1

    }

    @Autowired
    private lateinit var bookBorrowFlowRepository: BookBorrowFlowRepository

    fun buildPageQueryBorrowFlowDto(page: Int): PageQueryBorrowBookDto {
        return PageQueryBorrowBookDto().apply {
            this.bookStatusList = listOf(BookBorrowStatusEnum.BORROWER.status, BookBorrowStatusEnum.ABOUT_TO_EXPIRE.status)
            this.page = page
            this.pageSize = page_size
        }
    }

    private fun filterExpireBorrowList(unReturnBookBorrowList: Page<BookBorrowFlowPo>): List<BookBorrowFlowPo> {
        return unReturnBookBorrowList.records.filter {
            if (it.returnTime == null) false
            Date().compareTo(it.returnTime) > NumberUtils.INTEGER_ZERO
        }
    }

    private fun filterBorrowList(unReturnBookBorrowList: Page<BookBorrowFlowPo>): List<BookBorrowFlowPo> {
        return unReturnBookBorrowList.records.filter {
            val returnDay = if (it.returnTime != null) {
                DateUtil.calDateDay(it.returnTime)
            } else {
                NumberUtils.INTEGER_ZERO
            }
            returnDay <= Constants.number_three && returnDay >= NumberUtils.INTEGER_ZERO
        }
    }

    /**
     * 定时任务
     * 1.获取还未归还的借阅记录
     * 2.判断归还日期是否小于等于3天，设置修改状态为2
     */
    @Scheduled(cron = "0 0 0 * * ?")
    fun unsetBookBorrowStatus() {
        logger.info("图书借阅记录，设置状态为即将到期，定时任务-开始")
        var page = NumberUtils.INTEGER_ONE
        var unReturnBookBorrowList = bookBorrowFlowRepository.pageQueryBorrowFlow(buildPageQueryBorrowFlowDto(page))
        if (unReturnBookBorrowList.records.isNullOrEmpty()) return
        val updateBorrowFlowList = mutableListOf<BookBorrowFlowPo>()
        val updateExpireBorrowFlowList = mutableListOf<BookBorrowFlowPo>()
        while (!unReturnBookBorrowList.records.isNullOrEmpty()) {
            //过滤出归还日期小于等于3天的借阅记录
            val borrowList = filterBorrowList(unReturnBookBorrowList)
            //过滤出超时未归还的 当前时间大于归还时间的
            val expireBorrowList = filterExpireBorrowList(unReturnBookBorrowList)
            updateExpireBorrowFlowList.addAll(expireBorrowList)
            updateBorrowFlowList.addAll(borrowList)
            page += NumberUtils.INTEGER_ONE
            unReturnBookBorrowList = bookBorrowFlowRepository.pageQueryBorrowFlow(buildPageQueryBorrowFlowDto(page))
        }
        //更新数据库状态
        bookBorrowFlowRepository.batchUpdateStatusByUid(updateBorrowFlowList.mapNotNull { it.uid }, BookBorrowStatusEnum.ABOUT_TO_EXPIRE.status)
        bookBorrowFlowRepository.batchUpdateStatusByUid(updateExpireBorrowFlowList.mapNotNull { it.uid }, BookBorrowStatusEnum.EXPIRE_TO_RETURN.status)
        logger.info("图书借阅记录，设置状态为即将到期，定时任务-结束")
    }

}