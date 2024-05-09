package com.edu.book.domain.area.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.book.domain.area.dto.QueryLevelInfoDto
import com.edu.book.infrastructure.po.area.LevelPo

/**
 * <p>
 * 层级表 服务类
 * </p>
 *
 * @author 
 * @since 2024-05-09 11:10:10
 */
interface LevelRepository : IService<LevelPo> {

    /**
     * 查询层级信息
     */
    fun queryLevelInfos(dto: QueryLevelInfoDto): List<LevelPo>?

}
