package com.edu.book.domain.book.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.book.infrastructure.po.book.BookDetailAgePo

/**
 * <p>
 * 图书年龄段 服务类
 * </p>
 *
 * @author 
 * @since 2024-03-27 20:38:19
 */
interface BookDetailAgeRepository : IService<BookDetailAgePo> {

    /**
     * 查询年龄段列表
     */
    fun findByBookUid(isbn: String, bookUid: String): List<BookDetailAgePo>

    /**
     * 批量查询
     */
    fun batchQueryBookAgeGroups(bookUids: List<String>): List<BookDetailAgePo>

}
