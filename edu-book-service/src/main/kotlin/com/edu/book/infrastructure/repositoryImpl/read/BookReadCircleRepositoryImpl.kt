package com.edu.book.infrastructure.repositoryImpl.read;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.book.domain.read.repository.BookReadCircleRepository
import com.edu.book.infrastructure.po.read.BookReadCirclePo
import com.edu.book.infrastructure.repositoryImpl.dao.read.BookReadCircleDao
import org.springframework.stereotype.Repository;

/**
 * 阅读圈表 服务实现类
 * @author 
 * @since 2024-06-03 22:47:56
 */

@Repository
class BookReadCircleRepositoryImpl : ServiceImpl<BookReadCircleDao, BookReadCirclePo>(), BookReadCircleRepository {

}
