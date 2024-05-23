package com.edu.book.infrastructure.repositoryImpl.book;

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtUpdateWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.book.domain.book.repository.BookDetailRepository
import com.edu.book.infrastructure.po.book.BookDetailPo
import com.edu.book.infrastructure.repositoryImpl.dao.book.BookDetailDao
import com.edu.book.infrastructure.util.limitOne
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository;

/**
 * 图书详情表 服务实现类
 * @author 
 * @since 2024-03-24 22:54:53
 */

@Repository
class BookDetailRepositoryImpl : ServiceImpl<BookDetailDao, BookDetailPo>(), BookDetailRepository {

    @Autowired
    private lateinit var bookDetailDao: BookDetailDao

    /**
     * 通过园区查询
     */
    override fun findByGardenUid(gardenUid: String): List<BookDetailPo>? {
        val wrapper = KtQueryWrapper(BookDetailPo::class.java)
            .eq(BookDetailPo::gardenUid, gardenUid)
        return bookDetailDao.selectList(wrapper)
    }

    /**
     * 更新
     */
    override fun updateByBookUid(po: BookDetailPo, bookUid: String) {
        val wrapper = KtUpdateWrapper(BookDetailPo::class.java)
            .eq(BookDetailPo::bookUid, bookUid)
        bookDetailDao.update(po, wrapper)
    }

    /**
     * 查询
     */
    override fun findByBookUid(bookUid: String): BookDetailPo? {
        val wrapper = KtQueryWrapper(BookDetailPo::class.java)
            .eq(BookDetailPo::bookUid, bookUid)
            .limitOne()
        return getOne(wrapper)
    }

    /**
     * 批量查询
     */
    override fun findByBookUids(bookUids: List<String>): List<BookDetailPo>? {
        if (bookUids.isNullOrEmpty()) return emptyList()
        val wrapper = KtQueryWrapper(BookDetailPo::class.java)
            .`in`(BookDetailPo::bookUid, bookUids)
        return bookDetailDao.selectList(wrapper)
    }

    /**
     * 删除
     */
    override fun deleteByBookUid(bookUid: String) {
        val wrapper = KtUpdateWrapper(BookDetailPo::class.java)
            .eq(BookDetailPo::bookUid, bookUid)
        remove(wrapper)
    }

}
