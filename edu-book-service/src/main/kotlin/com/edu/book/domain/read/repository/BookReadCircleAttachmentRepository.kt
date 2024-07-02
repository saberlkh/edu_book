package com.edu.book.domain.read.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.book.infrastructure.po.read.BookReadCircleAttachmentPo

/**
 * <p>
 * 阅读圈附件表 服务类
 * </p>
 *
 * @author 
 * @since 2024-06-03 22:47:56
 */
interface BookReadCircleAttachmentRepository : IService<BookReadCircleAttachmentPo> {

    /**
     * 批量查询
     */
    fun batchQueryByCircleUids(circleUids: List<String>): List<BookReadCircleAttachmentPo>?

    /**
     * 删除附件
     */
    fun deleteByCircleUid(circleUid: String)

}
