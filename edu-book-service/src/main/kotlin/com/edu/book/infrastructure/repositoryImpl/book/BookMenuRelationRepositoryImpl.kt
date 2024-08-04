package com.edu.book.infrastructure.repositoryImpl.book;

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtUpdateWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.book.domain.book.repository.BookMenuRelationRepository
import com.edu.book.infrastructure.po.book.BookMenuRelationPo
import com.edu.book.infrastructure.repositoryImpl.dao.book.BookMenuRelationDao
import org.springframework.stereotype.Repository;

/**
 * 书单关联表 服务实现类
 * @author 
 * @since 2024-07-28 22:04:12
 */

@Repository
class BookMenuRelationRepositoryImpl : ServiceImpl<BookMenuRelationDao, BookMenuRelationPo>(), BookMenuRelationRepository {

    /**
     * 根据书单uid查询
     */
    override fun getByMenuUid(bookMenuUid: String): List<BookMenuRelationPo>? {
        val wrapper = KtQueryWrapper(BookMenuRelationPo::class.java)
            .eq(BookMenuRelationPo::bookMenuUid, bookMenuUid)
        return list(wrapper)
    }

    /**
     * 批量查询
     */
    override fun getByMenuUids(bookMenuUids: List<String>): List<BookMenuRelationPo>? {
        val wrapper = KtQueryWrapper(BookMenuRelationPo::class.java)
            .`in`(BookMenuRelationPo::bookMenuUid, bookMenuUids)
        return list(wrapper)
    }

    /**
     * 删除
     */
    override fun deleteByMenuUid(bookMenuUid: String) {
        val wrapper = KtUpdateWrapper(BookMenuRelationPo::class.java)
            .eq(BookMenuRelationPo::bookMenuUid, bookMenuUid)
        remove(wrapper)
    }

}
