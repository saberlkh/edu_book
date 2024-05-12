package com.edu.book.infrastructure.repositoryImpl.area;

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtUpdateWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.book.domain.area.dto.QueryLevelInfoDto
import com.edu.book.domain.area.enums.LevelTypeEnum
import com.edu.book.domain.area.repository.LevelRepository
import com.edu.book.infrastructure.po.area.LevelPo
import com.edu.book.infrastructure.repositoryImpl.dao.area.LevelDao
import com.edu.book.infrastructure.util.limitOne
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
            .eq(!dto.parentUid.isNullOrBlank(), LevelPo::parentUid, dto.parentUid)
        return levelDao.selectList(wrapper)
    }

    /**
     * 查询
     */
    override fun queryByUid(levelUid: String): LevelPo? {
        val wrapper = KtQueryWrapper(LevelPo::class.java)
            .eq(LevelPo::uid, levelUid)
        return levelDao.selectOne(wrapper)
    }

    /**
     * 查询
     */
    override fun queryByUid(levelUid: String, levelType: LevelTypeEnum): LevelPo? {
        val wrapper = KtQueryWrapper(LevelPo::class.java)
            .eq(LevelPo::uid, levelUid)
            .eq(LevelPo::levelType, levelType.type)
            .limitOne()
        return this.levelDao.selectOne(wrapper)
    }

    /**
     * 删除
     */
    override fun deleteByUids(levelUids: List<String>) {
        val wrapper = KtUpdateWrapper(LevelPo::class.java)
            .`in`(LevelPo::uid, levelUids)
        remove(wrapper)
    }

    /**
     * 根据父级查询
     */
    override fun queryByParentUid(parentUids: List<String>): List<LevelPo>? {
        val wrapper = KtQueryWrapper(LevelPo::class.java)
            .`in`(LevelPo::parentUid, parentUids)
        return levelDao.selectList(wrapper)
    }

}
