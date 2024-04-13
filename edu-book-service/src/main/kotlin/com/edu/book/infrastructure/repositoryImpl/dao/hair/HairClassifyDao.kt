package com.edu.book.infrastructure.repositoryImpl.dao.hair;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edu.book.infrastructure.po.hair.HairClassifyPo
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 美发分类表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2024-04-13 16:28:10
 */
@Mapper
@Repository
interface HairClassifyDao : BaseMapper<HairClassifyPo> {

}
