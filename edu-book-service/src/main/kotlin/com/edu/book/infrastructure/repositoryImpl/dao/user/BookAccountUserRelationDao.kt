package com.edu.book.infrastructure.repositoryImpl.dao.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edu.book.infrastructure.po.user.BookAccountUserRelationPo
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 账号用户关联表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2024-03-13 22:23:55
 */
@Mapper
@Repository
interface BookAccountUserRelationDao : BaseMapper<BookAccountUserRelationPo> {

}
