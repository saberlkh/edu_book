package com.edu.book.infrastructure.repositoryImpl.user;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.book.domain.user.repository.BookAccountRoleRelationRepository
import com.edu.book.infrastructure.po.user.BookAccountRoleRelationPo
import com.edu.book.infrastructure.repositoryImpl.dao.user.BookAccountRoleRelationDao
import org.springframework.stereotype.Repository;

/**
 * 账户角色关联表 服务实现类
 * @author 
 * @since 2024-03-13 22:23:55
 */

@Repository
class BookAccountRoleRelationRepositoryImpl : ServiceImpl<BookAccountRoleRelationDao, BookAccountRoleRelationPo>(), BookAccountRoleRelationRepository {

}
