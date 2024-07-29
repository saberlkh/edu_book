package com.edu.book.domain.book.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.book.infrastructure.po.book.BookMenuPo

/**
 * <p>
 * 书单表 服务类
 * </p>
 *
 * @author 
 * @since 2024-07-02 23:32:18
 */
interface BookMenuRepository : IService<BookMenuPo> {

    /**
     * 获取书单
     */
    fun getByUid(bookMenuUid: String): BookMenuPo?

    /**
     * 更新
     */
    fun modifyByUid(po: BookMenuPo)

}
