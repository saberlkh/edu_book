package com.edu.book.infrastructure.repositoryImpl.book;

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.book.domain.book.repository.BookDetailAgeRepository
import com.edu.book.infrastructure.po.book.BookDetailAgePo
import com.edu.book.infrastructure.repositoryImpl.dao.book.BookDetailAgeDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository;

/**
 * 图书年龄段 服务实现类
 * @author 
 * @since 2024-03-27 20:38:19
 */

@Repository
class BookDetailAgeRepositoryImpl : ServiceImpl<BookDetailAgeDao, BookDetailAgePo>(), BookDetailAgeRepository {

    @Autowired
    private lateinit var bookDetailAgeDao: BookDetailAgeDao

    /**
     * 查询年龄段列表
     */
    override fun findByBookUid(isbn: String, bookUid: String): List<BookDetailAgePo> {
        val wrapper = KtQueryWrapper(BookDetailAgePo::class.java)
            .eq(BookDetailAgePo::isbnCode, isbn)
            .eq(BookDetailAgePo::bookUid, bookUid)
        return bookDetailAgeDao.selectList(wrapper)
    }

}
