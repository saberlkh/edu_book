package com.edu.book.domain.book.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.book.infrastructure.po.book.BookMenuRelationPo

/**
 * <p>
 * 书单关联表 服务类
 * </p>
 *
 * @author 
 * @since 2024-07-28 22:04:12
 */
interface BookMenuRelationRepository : IService<BookMenuRelationPo> {

    /**
     * 根据书单Uid查询
     */
    fun getByMenuUid(bookMenuUid: String): List<BookMenuRelationPo>?

    /**
     * 批量查询
     */
    fun getByMenuUids(bookMenuUids: List<String>): List<BookMenuRelationPo>?

    /**
     * 删除
     */
    fun deleteByMenuUid(bookMenuUid: String)

    /**
     * 删除书单关联书籍
     */
    fun removeByBookMenuUid(bookMenuUid: String)

}
