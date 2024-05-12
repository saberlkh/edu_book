package com.edu.book.infrastructure.repositoryImpl.user;

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.book.domain.user.repository.BookRoleBasicRepository
import com.edu.book.infrastructure.po.user.BookRoleBasicPo
import com.edu.book.infrastructure.repositoryImpl.dao.user.BookRoleBasicDao
import com.edu.book.infrastructure.util.limitOne
import org.springframework.stereotype.Repository;

/**
 * 角色基础表 服务实现类
 * @author 
 * @since 2024-03-13 22:23:55
 */

@Repository
class BookRoleBasicRepositoryImpl : ServiceImpl<BookRoleBasicDao, BookRoleBasicPo>(), BookRoleBasicRepository {

    /**
     * 查询角色
     */
    override fun queryByRoleCode(roleCode: String): BookRoleBasicPo? {
        val wrapper = KtQueryWrapper(BookRoleBasicPo::class.java)
            .eq(BookRoleBasicPo::roleCode, roleCode)
            .limitOne()
        return this.getOne(wrapper)
    }

}
