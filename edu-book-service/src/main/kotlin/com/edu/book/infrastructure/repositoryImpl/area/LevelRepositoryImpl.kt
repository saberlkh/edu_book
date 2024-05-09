package com.edu.book.infrastructure.repositoryImpl.area;

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.book.domain.area.dto.QueryLevelInfoDto
import com.edu.book.domain.area.repository.LevelRepository
import com.edu.book.infrastructure.po.area.LevelPo
import com.edu.book.infrastructure.repositoryImpl.dao.area.LevelDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository;

/**
 * 层级表 服务实现类
 * @author 
 * @since 2024-05-09 11:10:10
 */

@Repository
class LevelRepositoryImpl : ServiceImpl<LevelDao, LevelPo>(), LevelRepository {

    @Autowired
    private lateinit var levelDao: LevelDao

    /**
     * 查询层级信息
     */
    override fun queryLevelInfos(dto: QueryLevelInfoDto): List<LevelPo>? {
        val wrapper = KtQueryWrapper(LevelPo::class.java)
            .eq(LevelPo::levelType, dto.levelType)
            .eq(!dto.provinceCode.isNullOrBlank(), LevelPo::provinceId, dto.provinceCode)
            .eq(!dto.cityCode.isNullOrBlank(), LevelPo::cityId, dto.cityCode)
            .eq(!dto.districtCode.isNullOrBlank(), LevelPo::districtId, dto.districtCode)
        return levelDao.selectList(wrapper)
    }

}
