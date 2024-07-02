package com.edu.book.infrastructure.repositoryImpl.book;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.book.domain.book.repository.BookMenuRepository
import com.edu.book.infrastructure.po.book.BookMenuPo
import com.edu.book.infrastructure.repositoryImpl.dao.book.BookMenuDao
import org.springframework.stereotype.Repository;

/**
 * 书单表 服务实现类
 * @author 
 * @since 2024-07-02 23:32:18
 */

@Repository
class BookMenuRepositoryImpl : ServiceImpl<BookMenuDao, BookMenuPo>(), BookMenuRepository {

}
