package com.edu.book.infrastructure.repositoryImpl.user;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.book.domain.user.repository.BookAccountRepository
import com.edu.book.infrastructure.po.user.BookAccountPo
import com.edu.book.infrastructure.repositoryImpl.dao.user.BookAccountDao
import org.springframework.stereotype.Repository;

/**
 * 账号表 服务实现类
 * @author 
 * @since 2024-03-13 22:23:55
 */

@Repository
class BookAccountRepositoryImpl : ServiceImpl<BookAccountDao, BookAccountPo>(), BookAccountRepository {

}
