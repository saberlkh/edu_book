package com.edu.book.infrastructure.repositoryImpl.user;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.book.domain.user.repository.BookUserRepository
import com.edu.book.infrastructure.po.user.BookUserPo
import com.edu.book.infrastructure.repositoryImpl.dao.user.BookUserDao
import org.springframework.stereotype.Repository;

/**
 * 用户表 服务实现类
 * @author 
 * @since 2024-03-13 22:23:55
 */

@Repository
class BookUserRepositoryImpl : ServiceImpl<BookUserDao, BookUserPo>(), BookUserRepository {

}
