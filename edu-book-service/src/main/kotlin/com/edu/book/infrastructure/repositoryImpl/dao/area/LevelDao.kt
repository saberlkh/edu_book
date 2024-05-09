package com.edu.book.infrastructure.repositoryImpl.dao.area;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edu.book.infrastructure.po.area.LevelPo
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 层级表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2024-05-09 11:10:10
 */
@Mapper
@Repository
interface LevelDao : BaseMapper<LevelPo> {

}
