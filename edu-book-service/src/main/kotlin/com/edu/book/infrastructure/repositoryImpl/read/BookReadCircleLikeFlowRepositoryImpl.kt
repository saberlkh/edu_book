package com.edu.book.infrastructure.repositoryImpl.read;

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtUpdateWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.book.domain.read.enums.ReadCircleLikeStatusEnum
import com.edu.book.domain.read.repository.BookReadCircleLikeFlowRepository
import com.edu.book.infrastructure.po.read.BookReadCircleLikeFlowPo
import com.edu.book.infrastructure.repositoryImpl.dao.read.BookReadCircleLikeFlowDao
import com.edu.book.infrastructure.util.limitOne
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository;

/**
 * 阅读圈点赞流水表 服务实现类
 * @author 
 * @since 2024-06-03 22:47:56
 */

@Repository
class BookReadCircleLikeFlowRepositoryImpl : ServiceImpl<BookReadCircleLikeFlowDao, BookReadCircleLikeFlowPo>(), BookReadCircleLikeFlowRepository {

    @Autowired
    private lateinit var bookReadCircleLikeFlowDao: BookReadCircleLikeFlowDao

    /**
     * 批量查询
     */
    override fun batchQueryByCircleUids(circleUids: List<String>): List<BookReadCircleLikeFlowPo>? {
        if (circleUids.isNullOrEmpty()) return emptyList()
        val wrapper = KtQueryWrapper(BookReadCircleLikeFlowPo::class.java)
            .`in`(BookReadCircleLikeFlowPo::readCircleUid, circleUids)
            .eq(BookReadCircleLikeFlowPo::likeStatus, ReadCircleLikeStatusEnum.LIKE.status)
        return bookReadCircleLikeFlowDao.selectList(wrapper)
    }

    /**
     * 查询用户点赞记录
     */
    override fun queryUserLike(circleUid: String, userUid: String): BookReadCircleLikeFlowPo? {
        val wrapper = KtQueryWrapper(BookReadCircleLikeFlowPo::class.java)
            .eq(BookReadCircleLikeFlowPo::readCircleUid, circleUid)
            .eq(BookReadCircleLikeFlowPo::userUid, userUid)
            .limitOne()
        return bookReadCircleLikeFlowDao.selectOne(wrapper)
    }

    /**
     * 更新
     */
    override fun updateByUid(po: BookReadCircleLikeFlowPo) {
        val wrapper = KtUpdateWrapper(BookReadCircleLikeFlowPo::class.java)
            .eq(BookReadCircleLikeFlowPo::uid, po.uid)
        update(po, wrapper)
    }

}
