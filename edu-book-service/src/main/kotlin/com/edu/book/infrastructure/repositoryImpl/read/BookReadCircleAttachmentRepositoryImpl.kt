package com.edu.book.infrastructure.repositoryImpl.read;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.book.domain.read.repository.BookReadCircleAttachmentRepository
import com.edu.book.infrastructure.po.read.BookReadCircleAttachmentPo
import com.edu.book.infrastructure.repositoryImpl.dao.read.BookReadCircleAttachmentDao
import org.springframework.stereotype.Repository;

/**
 * 阅读圈附件表 服务实现类
 * @author 
 * @since 2024-06-03 22:47:56
 */

@Repository
class BookReadCircleAttachmentRepositoryImpl : ServiceImpl<BookReadCircleAttachmentDao, BookReadCircleAttachmentPo>(), BookReadCircleAttachmentRepository {

}
