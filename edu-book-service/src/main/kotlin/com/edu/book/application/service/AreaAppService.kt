package com.edu.book.application.service

import com.edu.book.domain.area.dto.LevelInfoDto
import com.edu.book.domain.area.dto.QueryAreaInfoDto
import com.edu.book.domain.area.dto.QueryLevelInfoDto
import com.edu.book.domain.area.dto.SaveLevelInfoDto
import com.edu.book.domain.area.service.AreaDomainService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @Auther: liukaihua
 * @Date: 2024/5/8 20:27
 * @Description:
 */

@Service
class AreaAppService {

    @Autowired
    private lateinit var areaDomainService: AreaDomainService

    /**
     * 查询层级信息
     */
    fun queryLevelInfo(dto: QueryLevelInfoDto): List<LevelInfoDto> {
        return areaDomainService.queryLevelInfo(dto)
    }

    /**
     * 查询地区列表
     */
    fun queryAreaInfo(areaCode: String?): List<QueryAreaInfoDto> {
        return areaDomainService.queryAreaInfo(areaCode)
    }

    /**
     * 添加层级信息
     */
    fun saveLevelInfo(dto: SaveLevelInfoDto) {
        areaDomainService.saveLevelInfo(dto)
    }

}