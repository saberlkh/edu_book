package com.edu.book.infrastructure.repositoryImpl.dao.read;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edu.book.infrastructure.po.read.BookReadCircleCommentFlowPo
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 阅读圈点赞流水表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2024-06-03 22:47:56
 */
@Mapper
@Repository
interface BookReadCircleCommentFlowDao : BaseMapper<BookReadCircleCommentFlowPo> {

}
