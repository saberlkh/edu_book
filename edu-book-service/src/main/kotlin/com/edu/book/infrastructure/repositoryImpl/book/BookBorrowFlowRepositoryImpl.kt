package com.edu.book.infrastructure.repositoryImpl.book;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.book.domain.book.repository.BookBorrowFlowRepository
import com.edu.book.infrastructure.po.book.BookBorrowFlowPo
import com.edu.book.infrastructure.repositoryImpl.dao.book.BookBorrowFlowDao
import org.springframework.stereotype.Repository;

/**
 * 图书借阅流水表 服务实现类
 * @author 
 * @since 2024-03-24 22:54:53
 */

@Repository
class BookBorrowFlowRepositoryImpl : ServiceImpl<BookBorrowFlowDao, BookBorrowFlowPo>(), BookBorrowFlowRepository {

}
