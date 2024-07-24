package com.edu.book.infrastructure.repositoryImpl.book;

import com.baomidou.mybatisplus.extension.kotlin.KtUpdateWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.book.domain.book.repository.BookMenuRepository
import com.edu.book.infrastructure.po.book.BookMenuPo
import com.edu.book.infrastructure.repositoryImpl.dao.book.BookMenuDao
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
    override fun deleteByBookUid(bookUid: String) {
        val wrapper = KtUpdateWrapper(BookMenuPo::class.java)
            .eq(BookMenuPo::bookUid, bookUid)
        update(wrapper)
    }

}
