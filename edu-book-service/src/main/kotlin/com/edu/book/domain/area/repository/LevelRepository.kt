package com.edu.book.domain.area.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.book.domain.area.dto.QueryLevelInfoDto
import com.edu.book.domain.area.enums.LevelTypeEnum
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

    /**
     * 查询
     */
    fun queryByUid(levelUid: String): LevelPo?

    /**
     * 查询
     */
    fun queryByUid(levelUid: String, levelType: LevelTypeEnum): LevelPo?

    /**
     * 删除
     */
    fun deleteByUids(levelUids: List<String>)

    /**
     * 根据父Uid查询
     */
    fun queryByParentUid(parentUids: List<String>): List<LevelPo>?

}
