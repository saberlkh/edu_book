package com.edu.book.infrastructure.repositoryImpl.book;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.book.domain.book.repository.BookDetailClassifyRepository
import com.edu.book.infrastructure.po.book.BookDetailClassifyPo
import com.edu.book.infrastructure.repositoryImpl.dao.book.BookDetailClassifyDao
import org.springframework.stereotype.Repository;

/**
 * 图书分类表 服务实现类
 * @author 
 * @since 2024-03-25 23:01:25
 */

@Repository
class BookDetailClassifyRepositoryImpl : ServiceImpl<BookDetailClassifyDao, BookDetailClassifyPo>(), BookDetailClassifyRepository {

}
