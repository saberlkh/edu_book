package com.edu.book.infrastructure.repositoryImpl.user;

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.book.domain.user.repository.BookAccountUserRelationRepository
import com.edu.book.infrastructure.po.user.BookAccountUserRelationPo
import com.edu.book.infrastructure.repositoryImpl.dao.user.BookAccountUserRelationDao
import com.edu.book.infrastructure.util.limitOne
import org.springframework.stereotype.Repository;

/**
 * 账号用户关联表 服务实现类
 * @author 
 * @since 2024-03-13 22:23:55
 */

@Repository
class BookAccountUserRelationRepositoryImpl : ServiceImpl<BookAccountUserRelationDao, BookAccountUserRelationPo>(), BookAccountUserRelationRepository {

    /**
     * 查询
     */
    override fun findByAccountUid(accountUid: String?): BookAccountUserRelationPo? {
        if (accountUid.isNullOrBlank()) return null
        val wrapper = KtQueryWrapper(BookAccountUserRelationPo::class.java)
            .eq(BookAccountUserRelationPo::accountUid, accountUid)
            .limitOne()
        return getOne(wrapper)
    }

}
