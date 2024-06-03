package com.edu.book.infrastructure.repositoryImpl.read;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.book.domain.read.repository.BookReadCircleLikeFlowRepository
import com.edu.book.infrastructure.po.read.BookReadCircleLikeFlowPo
import com.edu.book.infrastructure.repositoryImpl.dao.read.BookReadCircleLikeFlowDao
import org.springframework.stereotype.Repository;

/**
 * 阅读圈点赞流水表 服务实现类
 * @author 
 * @since 2024-06-03 22:47:56
 */

@Repository
class BookReadCircleLikeFlowRepositoryImpl : ServiceImpl<BookReadCircleLikeFlowDao, BookReadCircleLikeFlowPo>(), BookReadCircleLikeFlowRepository {

}
