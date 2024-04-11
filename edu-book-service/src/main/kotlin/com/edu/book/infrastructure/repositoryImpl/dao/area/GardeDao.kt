package com.edu.book.infrastructure.repositoryImpl.dao.area;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edu.book.infrastructure.po.area.GardePo
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 年级表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2024-04-10 23:03:42
 */
@Mapper
@Repository
interface GardeDao : BaseMapper<GardePo> {

}
