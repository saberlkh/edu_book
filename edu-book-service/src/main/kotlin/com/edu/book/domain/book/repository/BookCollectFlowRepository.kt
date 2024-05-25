package com.edu.book.domain.book.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.book.infrastructure.po.book.BookCollectFlowPo

/**
 * <p>
 * 图书收藏表 服务类
 * </p>
 *
 * @author 
 * @since 2024-05-25 20:53:04
 */
interface BookCollectFlowRepository : IService<BookCollectFlowPo> {

    /**
     * 查询收藏记录
     */
    fun findUserCollectFlowByBookUid(userUid: String, bookUid: String): BookCollectFlowPo?

    /**
     * 更新
     */
    fun updateBookCollectFlow(po: BookCollectFlowPo)

}
