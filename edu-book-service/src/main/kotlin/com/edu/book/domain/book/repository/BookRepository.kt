package com.edu.book.domain.book.repository;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.book.domain.book.dto.PageQueryBookDto
import com.edu.book.domain.book.dto.PageQueryBookResultEntity
import com.edu.book.infrastructure.po.book.BookPo

/**
 * <p>
 * 图书表 服务类
 * </p>
 *
 * @author 
 * @since 2024-03-24 22:54:53
 */
interface BookRepository : IService<BookPo> {

    /**
     * 查询isbn列表
     */
    fun findIsbnList(garden: String?, isbn: String?): List<BookPo>

    /**
     * 根据isbn查询
     */
    fun findByIsbnCode(isbnCode: String?): BookPo?

    /**
     * 批量查询
     */
    fun findByIsbnCodes(isbnCodes: List<String>?): List<BookPo>?

    /**
     * 更新
     */
    fun updateByUid(po: BookPo)

    /**
     * 分页查询
     */
    fun pageQueryBooks(dto: PageQueryBookDto): Page<PageQueryBookResultEntity>

}
