package com.edu.book.domain.read.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.book.infrastructure.po.read.BookReadCircleCommentFlowPo

/**
 * <p>
 * 阅读圈点赞流水表 服务类
 * </p>
 *
 * @author 
 * @since 2024-06-03 22:47:56
 */
interface BookReadCircleCommentFlowRepository : IService<BookReadCircleCommentFlowPo> {

    /**
     * 批量查询
     */
    fun batchQueryByCircleUids(circleUids: List<String>): List<BookReadCircleCommentFlowPo>?

}
