package com.edu.book.infrastructure.repositoryImpl.user;

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.book.domain.user.repository.BookAccountRoleRelationRepository
import com.edu.book.infrastructure.po.user.BookAccountRoleRelationPo
import com.edu.book.infrastructure.repositoryImpl.dao.user.BookAccountRoleRelationDao
import com.edu.book.infrastructure.util.limitOne
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository;

/**
 * 账户角色关联表 服务实现类
 * @author 
 * @since 2024-03-13 22:23:55
 */

@Repository
class BookAccountRoleRelationRepositoryImpl : ServiceImpl<BookAccountRoleRelationDao, BookAccountRoleRelationPo>(), BookAccountRoleRelationRepository {

    @Autowired
    private lateinit var bookAccountRoleRelationDao: BookAccountRoleRelationDao

    /**
     * 根据账号udi查询
     */
    override fun findByAccountUid(accountUid: String?): BookAccountRoleRelationPo? {
        if (accountUid.isNullOrBlank()) return null
        val wrapper = KtQueryWrapper(BookAccountRoleRelationPo::class.java)
            .eq(BookAccountRoleRelationPo::accountUid, accountUid)
            .limitOne()
        return getOne(wrapper)
    }

}
