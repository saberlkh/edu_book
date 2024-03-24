package com.edu.book.infrastructure.repositoryImpl.book;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.book.domain.book.repository.BookDetailRepository
import com.edu.book.infrastructure.po.book.BookDetailPo
import com.edu.book.infrastructure.repositoryImpl.dao.book.BookDetailDao
import org.springframework.stereotype.Repository;

/**
 * 图书详情表 服务实现类
 * @author 
 * @since 2024-03-24 22:54:53
 */

@Repository
class BookDetailRepositoryImpl : ServiceImpl<BookDetailDao, BookDetailPo>(), BookDetailRepository {

}
