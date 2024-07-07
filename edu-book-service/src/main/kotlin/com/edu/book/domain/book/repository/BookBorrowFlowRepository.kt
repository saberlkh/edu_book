package com.edu.book.domain.book.repository;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.book.domain.book.dto.PageQueryBorrowBookDto
import com.edu.book.infrastructure.po.book.BookBorrowFlowPo

/**
 * <p>
 * 图书借阅流水表 服务类
 * </p>
 *
 * @author 
 * @since 2024-03-24 22:54:53
 */
interface BookBorrowFlowRepository : IService<BookBorrowFlowPo> {

    /**
     * 批量查询
     */
    fun batchQueryByUserUid(userUids: List<String>): List<BookBorrowFlowPo>?

    /**
     * 更新流水
     */
    fun updateByUid(po: BookBorrowFlowPo)

    /**
     * 批量查询
     */
    fun batchQueryByBorrowCardIds(borrowCardIds: List<String>, borrowStatus: Int): List<BookBorrowFlowPo>?

    /**
     * 分页查询
     */
    fun pageQueryBorrowFlow(dto: PageQueryBorrowBookDto): Page<BookBorrowFlowPo>

    /**
     * 查询借阅记录
     */
    fun findByBookUid(bookUid: String): BookBorrowFlowPo?

    /**
     * 删除
     */
    fun deleteByBookUid(bookUid: String)

    /**
     * 批量更新
     */
    fun batchUpdateStatusByUid(uids: List<String>, borrowStatus: Int)

}
