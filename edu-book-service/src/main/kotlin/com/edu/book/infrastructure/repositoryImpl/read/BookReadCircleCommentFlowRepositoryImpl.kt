package com.edu.book.infrastructure.repositoryImpl.read;

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.book.domain.read.repository.BookReadCircleCommentFlowRepository
import com.edu.book.infrastructure.po.read.BookReadCircleCommentFlowPo
import com.edu.book.infrastructure.repositoryImpl.dao.read.BookReadCircleCommentFlowDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository;

/**
 * 阅读圈点赞流水表 服务实现类
 * @author 
 * @since 2024-06-03 22:47:56
 */

@Repository
class BookReadCircleCommentFlowRepositoryImpl : ServiceImpl<BookReadCircleCommentFlowDao, BookReadCircleCommentFlowPo>(), BookReadCircleCommentFlowRepository {

    @Autowired
    private lateinit var bookReadCircleCommentFlowDao: BookReadCircleCommentFlowDao

    /**
     * 批量查询
     */
    override fun batchQueryByCircleUids(circleUids: List<String>): List<BookReadCircleCommentFlowPo>? {
        if (circleUids.isNullOrEmpty()) return emptyList()
        val wrapper = KtQueryWrapper(BookReadCircleCommentFlowPo::class.java)
            .`in`(BookReadCircleCommentFlowPo::readCircleUid, circleUids)
        return bookReadCircleCommentFlowDao.selectList(wrapper)
    }

}
