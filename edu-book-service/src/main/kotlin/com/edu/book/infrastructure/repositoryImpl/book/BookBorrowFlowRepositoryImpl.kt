package com.edu.book.infrastructure.repositoryImpl.book;

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtUpdateWrapper
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.book.domain.book.dto.PageQueryBorrowBookDto
import com.edu.book.domain.book.repository.BookBorrowFlowRepository
import com.edu.book.infrastructure.po.book.BookBorrowFlowPo
import com.edu.book.infrastructure.repositoryImpl.dao.book.BookBorrowFlowDao
import com.edu.book.infrastructure.util.limitOne
import org.apache.commons.lang3.math.NumberUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository;

/**
 * 图书借阅流水表 服务实现类
 * @author 
 * @since 2024-03-24 22:54:53
 */

@Repository
class BookBorrowFlowRepositoryImpl : ServiceImpl<BookBorrowFlowDao, BookBorrowFlowPo>(), BookBorrowFlowRepository {

    @Autowired
    private lateinit var bookBorrowFlowDao: BookBorrowFlowDao

    /**
     * 更新流水
     */
    override fun updateByUid(po: BookBorrowFlowPo) {
        val wrapper = KtUpdateWrapper(BookBorrowFlowPo::class.java)
            .eq(BookBorrowFlowPo::uid, po.uid)
        update(po, wrapper)
    }

    /**
     * 批量查询
     */
    override fun batchQueryByBorrowCardIds(borrowCardIds: List<String>, borrowStatus: Int): List<BookBorrowFlowPo>? {
        val wrapper = KtQueryWrapper(BookBorrowFlowPo::class.java)
            .`in`(BookBorrowFlowPo::borrowCardId, borrowCardIds)
            .eq(BookBorrowFlowPo::borrowStatus, borrowStatus)
        return bookBorrowFlowDao.selectList(wrapper)
    }

    /**
     * 批量查询
     */
    override fun batchQueryByUserUid(userUids: List<String>): List<BookBorrowFlowPo>? {
        val wrapper = KtQueryWrapper(BookBorrowFlowPo::class.java)
            .`in`(BookBorrowFlowPo::borrowUserUid, userUids)
        return list(wrapper)
    }

    /**
     * 分页查询
     */
    override fun pageQueryBorrowFlow(dto: PageQueryBorrowBookDto): Page<BookBorrowFlowPo> {
        val totalCount = bookBorrowFlowDao.getBorrowFlowTotal(dto)
        if (totalCount <= NumberUtils.INTEGER_ZERO) return Page()
        val result = bookBorrowFlowDao.getPageBorrowFlow(dto)
        val page = Page<BookBorrowFlowPo>(dto.page.toLong(), dto.pageSize.toLong(), totalCount.toLong())
        page.records = result
        return page
    }

    /**
     * 查询借阅记录
     */
    override fun findByBookUid(bookUid: String): BookBorrowFlowPo? {
        val wrapper = KtQueryWrapper(BookBorrowFlowPo::class.java)
            .eq(BookBorrowFlowPo::bookUid, bookUid)
            .limitOne()
        return getOne(wrapper)
    }

    /**
     * 删除
     */
    override fun deleteByBookUid(bookUid: String) {
        val wrapper = KtUpdateWrapper(BookBorrowFlowPo::class.java)
            .eq(BookBorrowFlowPo::bookUid, bookUid)
        remove(wrapper)
    }

    /**
     * 批量更新
     */
    override fun batchUpdateStatusByUid(uids: List<String>, borrowStatus: Int) {
        if (uids.isNullOrEmpty()) return
        val wrapper = KtUpdateWrapper(BookBorrowFlowPo::class.java)
            .`in`(BookBorrowFlowPo::uid, uids)
            .set(BookBorrowFlowPo::borrowStatus, borrowStatus)
        update(wrapper)
    }

}
