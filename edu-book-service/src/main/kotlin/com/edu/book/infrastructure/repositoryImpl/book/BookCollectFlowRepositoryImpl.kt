package com.edu.book.infrastructure.repositoryImpl.book;

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtUpdateWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.book.domain.book.repository.BookCollectFlowRepository
import com.edu.book.infrastructure.po.book.BookCollectFlowPo
import com.edu.book.infrastructure.repositoryImpl.dao.book.BookCollectFlowDao
import com.edu.book.infrastructure.util.limitOne
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository;

/**
 * 图书收藏表 服务实现类
 * @author 
 * @since 2024-05-25 20:53:04
 */

@Repository
class BookCollectFlowRepositoryImpl : ServiceImpl<BookCollectFlowDao, BookCollectFlowPo>(), BookCollectFlowRepository {

    @Autowired
    private lateinit var bookCollectFlowDao: BookCollectFlowDao

    /**
     * 查询收藏记录
     */
    override fun findUserCollectFlowByBookUid(userUid: String, bookUid: String): BookCollectFlowPo? {
        val wrapper = KtQueryWrapper(BookCollectFlowPo::class.java)
            .eq(BookCollectFlowPo::userUid, userUid)
            .eq(BookCollectFlowPo::bookUid, bookUid)
            .limitOne()
        return bookCollectFlowDao.selectOne(wrapper)
    }

    /**
     * 更新
     */
    override fun updateBookCollectFlow(po: BookCollectFlowPo) {
        val wrapper = KtUpdateWrapper(BookCollectFlowPo::class.java)
            .eq(BookCollectFlowPo::uid, po.uid)
        bookCollectFlowDao.update(po, wrapper)
    }

}
