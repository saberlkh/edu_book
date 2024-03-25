package com.edu.book.infrastructure.repositoryImpl.book;

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtUpdateWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.book.domain.book.repository.BookRepository
import com.edu.book.infrastructure.po.book.BookPo
import com.edu.book.infrastructure.repositoryImpl.dao.book.BookDao
import com.edu.book.infrastructure.util.limitOne
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository;

/**
 * 图书表 服务实现类
 * @author 
 * @since 2024-03-24 22:54:53
 */

@Repository
class BookRepositoryImpl : ServiceImpl<BookDao, BookPo>(), BookRepository {

    @Autowired
    private lateinit var bookDao: BookDao

    /**
     * 根据isbn查询
     */
    override fun findByIsbnCode(isbnCode: String?): BookPo? {
        if (isbnCode.isNullOrBlank()) return null
        val wrapper = KtQueryWrapper(BookPo::class.java)
            .eq(BookPo::isbnCode, isbnCode)
            .limitOne()
        return getOne(wrapper)
    }

    /**
     * 更新
     */
    override fun updateByUid(po: BookPo) {
        val updateWrapper = KtUpdateWrapper(BookPo::class.java)
            .eq(BookPo::uid, po.uid)
        update(po, updateWrapper)
    }

}
