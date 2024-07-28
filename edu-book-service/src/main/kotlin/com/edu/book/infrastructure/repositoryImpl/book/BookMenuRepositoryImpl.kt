package com.edu.book.infrastructure.repositoryImpl.book;

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtUpdateWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.book.domain.book.repository.BookMenuRepository
import com.edu.book.infrastructure.po.book.BookMenuPo
import com.edu.book.infrastructure.repositoryImpl.dao.book.BookMenuDao
import com.edu.book.infrastructure.util.limitOne
import org.springframework.stereotype.Repository;

/**
 * 书单表 服务实现类
 * @author 
 * @since 2024-07-02 23:32:18
 */

@Repository
class BookMenuRepositoryImpl : ServiceImpl<BookMenuDao, BookMenuPo>(), BookMenuRepository {

    /**
     * 删除
     */
    override fun deleteByIsbn(isbn: String) {
        val wrapper = KtUpdateWrapper(BookMenuPo::class.java)
            .eq(BookMenuPo::isbn, isbn)
        update(wrapper)
    }

    /**
     * 查询
     */
    override fun findByIsbn(isbn: String): BookMenuPo? {
        val wrapper = KtQueryWrapper(BookMenuPo::class.java)
            .eq(BookMenuPo::isbn, isbn)
            .limitOne()
        return getOne(wrapper)
    }

    /**
     * 查询书单信息
     */
    override fun findBookMenus(): List<BookMenuPo>? {
        val wrapper = KtQueryWrapper(BookMenuPo::class.java)
        return this.list()
    }

}
