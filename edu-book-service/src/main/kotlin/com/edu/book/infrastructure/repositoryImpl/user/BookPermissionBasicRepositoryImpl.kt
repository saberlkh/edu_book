package com.edu.book.infrastructure.repositoryImpl.user;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.book.domain.user.repository.BookPermissionBasicRepository
import com.edu.book.infrastructure.po.user.BookPermissionBasicPo
import com.edu.book.infrastructure.repositoryImpl.dao.user.BookPermissionBasicDao
import org.springframework.stereotype.Repository;

/**
 * 权限基础表 服务实现类
 * @author 
 * @since 2024-03-13 22:23:55
 */

@Repository
class BookPermissionBasicRepositoryImpl : ServiceImpl<BookPermissionBasicDao, BookPermissionBasicPo>(), BookPermissionBasicRepository {

}
