package com.edu.book.domain.read.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.book.infrastructure.po.read.BookReadCircleLikeFlowPo

/**
 * <p>
 * 阅读圈点赞流水表 服务类
 * </p>
 *
 * @author 
 * @since 2024-06-03 22:47:56
 */
interface BookReadCircleLikeFlowRepository : IService<BookReadCircleLikeFlowPo> {

    /**
     * 批量查询
     */
    fun batchQueryByCircleUids(circleUids: List<String>): List<BookReadCircleLikeFlowPo>?

    /**
     * 查询用户点赞记录
     */
    fun queryUserLike(circleUid: String, userUid: String): BookReadCircleLikeFlowPo?

    /**
     * 更新
     */
    fun updateByUid(po: BookReadCircleLikeFlowPo)

}
