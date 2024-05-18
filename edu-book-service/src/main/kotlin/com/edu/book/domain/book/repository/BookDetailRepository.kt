package com.edu.book.domain.book.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.book.infrastructure.po.book.BookDetailPo

/**
 * <p>
 * 图书详情表 服务类
 * </p>
 *
 * @author 
 * @since 2024-03-24 22:54:53
 */
interface BookDetailRepository : IService<BookDetailPo> {

    /**
     * 查询
     */
    fun findByBookUid(bookUid: String): BookDetailPo?

    /**
     * 删除
     */
    fun deleteByBookUid(bookUid: String)

    /**
     * 通过园区查询
     */
    fun findByGardenUid(gardenUid: String): List<BookDetailPo>?

    /**
     * 更新
     */
    fun updateByBookUid(po: BookDetailPo, bookUid: String)

}
