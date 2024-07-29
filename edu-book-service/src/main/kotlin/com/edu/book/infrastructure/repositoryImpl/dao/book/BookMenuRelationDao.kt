package com.edu.book.infrastructure.repositoryImpl.dao.book;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edu.book.infrastructure.po.book.BookMenuRelationPo
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 书单关联表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2024-07-28 22:04:12
 */
@Mapper
@Repository
interface BookMenuRelationDao : BaseMapper<BookMenuRelationPo> {

}
