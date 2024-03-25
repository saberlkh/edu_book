package com.edu.book.domain.book.repository;

import com.baomidou.mybatisplus.extension.service.IService;
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
     * 根据isbn查询
     */
    fun findByIsbnCode(isbnCode: String?): BookPo?

    /**
     * 更新
     */
    fun updateByUid(po: BookPo)

}
