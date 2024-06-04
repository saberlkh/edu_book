package com.edu.book.infrastructure.repositoryImpl.read;

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.book.domain.read.repository.BookReadCircleAttachmentRepository
import com.edu.book.infrastructure.po.read.BookReadCircleAttachmentPo
import com.edu.book.infrastructure.repositoryImpl.dao.read.BookReadCircleAttachmentDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository;

/**
 * 阅读圈附件表 服务实现类
 * @author 
 * @since 2024-06-03 22:47:56
 */

@Repository
class BookReadCircleAttachmentRepositoryImpl : ServiceImpl<BookReadCircleAttachmentDao, BookReadCircleAttachmentPo>(), BookReadCircleAttachmentRepository {

    @Autowired
    private lateinit var bookReadCircleAttachmentDao: BookReadCircleAttachmentDao

    /**
     * 批量查询
     */
    override fun batchQueryByCircleUids(circleUids: List<String>): List<BookReadCircleAttachmentPo>? {
        if (circleUids.isNullOrEmpty()) return emptyList()
        val wrapper = KtQueryWrapper(BookReadCircleAttachmentPo::class.java)
            .`in`(BookReadCircleAttachmentPo::readCircleUid, circleUids)
        return bookReadCircleAttachmentDao.selectList(wrapper)
    }

}
