package com.edu.book.domain.book.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.book.infrastructure.po.book.BookDetailClassifyPo

/**
 * <p>
 * 图书分类表 服务类
 * </p>
 *
 * @author 
 * @since 2024-03-25 23:01:25
 */
interface BookDetailClassifyRepository : IService<BookDetailClassifyPo> {

    /**
     * 查询列表
     */
    fun findClassifyList(bookUid: String, isbnCode: String): List<BookDetailClassifyPo>

    /**
     * 批量查询
     */
    fun batchQueryClassifyList(bookUids: List<String>): List<BookDetailClassifyPo>

}
