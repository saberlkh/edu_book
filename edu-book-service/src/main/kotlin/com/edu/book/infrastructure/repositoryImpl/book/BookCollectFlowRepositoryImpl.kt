package com.edu.book.infrastructure.repositoryImpl.book;

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtUpdateWrapper
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.book.domain.book.dto.PageQueryUserBookCollectParam
import com.edu.book.domain.book.repository.BookCollectFlowRepository
import com.edu.book.infrastructure.po.book.BookBorrowFlowPo
import com.edu.book.infrastructure.po.book.BookCollectFlowPo
import com.edu.book.infrastructure.repositoryImpl.dao.book.BookCollectFlowDao
import com.edu.book.infrastructure.util.limitOne
import org.apache.commons.lang3.math.NumberUtils
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

    /**
     * 分页查询
     */
    override fun pageQueryBookCollect(param: PageQueryUserBookCollectParam): Page<BookCollectFlowPo> {
        val totalCount = bookCollectFlowDao.getBookCollectTotal(param)
        if (totalCount <= NumberUtils.INTEGER_ZERO) return Page()
        val result = bookCollectFlowDao.getPageListBookCollect(param)
        val page = Page<BookCollectFlowPo>(param.page.toLong(), param.pageSize.toLong(), totalCount.toLong())
        page.records = result
        return page
    }

    /**
     * 删除
     */
    override fun deleteByBookUid(bookUid: String) {
        val wrapper = KtUpdateWrapper(BookCollectFlowPo::class.java)
            .eq(BookCollectFlowPo::bookUid, bookUid)
        remove(wrapper)
    }

}
