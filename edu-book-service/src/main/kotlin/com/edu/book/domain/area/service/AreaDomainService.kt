package com.edu.book.domain.area.service

import com.edu.book.domain.area.dto.QueryAreaInfoDto
import com.edu.book.domain.area.dto.LevelInfoDto
import com.edu.book.domain.area.dto.QueryLevelInfoDto
import com.edu.book.domain.area.dto.SaveLevelInfoDto
import com.edu.book.domain.area.enums.AreaTypeEnum
import com.edu.book.domain.area.repository.AreaRepository
import com.edu.book.domain.area.repository.LevelRepository
import com.edu.book.infrastructure.po.area.LevelPo
import com.edu.book.infrastructure.util.MapperUtil
import com.edu.book.infrastructure.util.UUIDUtil
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @Auther: liukaihua
 * @Date: 2024/4/10 23:22
 * @Description:
 */

@Service
class AreaDomainService {

    private val logger = LoggerFactory.getLogger(AreaDomainService::class.java)

    @Autowired
    private lateinit var areaRepository: AreaRepository

    @Autowired
    private lateinit var levelRepository: LevelRepository

    /**
     * 查询层级信息
     */
    fun queryLevelInfo(dto: QueryLevelInfoDto): List<LevelInfoDto> {
        val pos = levelRepository.queryLevelInfos(dto) ?: return emptyList()
        return pos.map {
            LevelInfoDto().apply {
                this.levelUid = it.uid!!
                this.levelName = it.levelName!!
            }
        }
    }

    /**
     * 添加层级信息
     */
    fun saveLevelInfo(dto: SaveLevelInfoDto) {
        //添加层级信息
        val levelPo = LevelPo().apply {
            this.uid = UUIDUtil.createUUID()
            this.levelName = dto.levelName
            this.levelType = dto.levelType
            this.parentUid = dto.parentUid ?: ""
            this.provinceId = dto.provinceCode
            this.cityId = dto.cityCode
            this.districtId = dto.districtCode
        }
        levelRepository.save(levelPo)
    }

    /**
     * 查询地区信息
     */
    fun queryAreaInfo(areaCode: String?): List<QueryAreaInfoDto> {
        val areaType = if (areaCode.isNullOrBlank()) {
            AreaTypeEnum.PROVINCE.type
        } else {
            null
        }
        //根据areaCode获取区域信息
        val parentUid = if (areaCode.isNullOrBlank()) {
            null
        } else {
            areaRepository.queryByAreaCode(areaCode)?.uid
        }
        val pos = areaRepository.queryByParentUid(parentUid, areaType) ?: return emptyList()
        return MapperUtil.mapToList(QueryAreaInfoDto::class.java, pos)
    }

    /**
     * 删除层级信息
     */
    fun deleteLevelInfo(levelUid: String) {
        //查询层级信息
        levelRepository.queryByUid(levelUid) ?: return
        //删除层级信息
        levelRepository.deleteByUids(listOf(levelUid))
        //查询子级列表
        var childLevels = levelRepository.queryByParentUid(listOf(levelUid))
        while (!childLevels.isNullOrEmpty()) {
            val childLevelUids = childLevels.mapNotNull { it.uid }
            levelRepository.deleteByUids(childLevelUids)
            childLevels = levelRepository.queryByParentUid(childLevelUids)
        }
    }

}