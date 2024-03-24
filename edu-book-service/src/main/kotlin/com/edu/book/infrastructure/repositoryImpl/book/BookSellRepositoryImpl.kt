package com.edu.book.infrastructure.repositoryImpl.book;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.book.domain.book.repository.BookSellRepository
import com.edu.book.infrastructure.po.book.BookSellPo
import com.edu.book.infrastructure.repositoryImpl.dao.book.BookSellDao
import org.springframework.stereotype.Repository;

/**
 * 图书售卖表 服务实现类
 * @author 
 * @since 2024-03-24 22:54:53
 */

@Repository
class BookSellRepositoryImpl : ServiceImpl<BookSellDao, BookSellPo>(), BookSellRepository {

}
