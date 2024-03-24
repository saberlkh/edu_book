package com.edu.book.infrastructure.repositoryImpl.book;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.book.domain.book.repository.BookRepository
import com.edu.book.infrastructure.po.book.BookPo
import com.edu.book.infrastructure.repositoryImpl.dao.book.BookDao
import org.springframework.stereotype.Repository;

/**
 * 图书表 服务实现类
 * @author 
 * @since 2024-03-24 22:54:53
 */

@Repository
class BookRepositoryImpl : ServiceImpl<BookDao, BookPo>(), BookRepository {

}
