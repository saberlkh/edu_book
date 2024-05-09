package com.edu.book.api.http.service

import com.edu.book.api.vo.area.QueryAreaInfoVo
import com.edu.book.api.vo.area.SaveLevelInfoVo
import com.edu.book.application.service.AreaAppService
import com.edu.book.domain.area.dto.SaveLevelInfoDto
import com.edu.book.infrastructure.util.MapperUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @Auther: liukaihua
 * @Date: 2024/5/8 20:28
 * @Description:
 */

@Service
class AreaWebService {

    @Autowired
    private lateinit var areaAppService: AreaAppService

    /**
     * 查询地区列表
     */
    fun queryAreaInfo(areaCode: String?): List<QueryAreaInfoVo> {
        val dtos = areaAppService.queryAreaInfo(areaCode)
        return MapperUtil.mapToList(QueryAreaInfoVo::class.java, dtos)
    }

    /**
     * 添加层级信息
     */
    fun saveLevelInfo(vo: SaveLevelInfoVo) {
        val dto = MapperUtil.map(SaveLevelInfoDto::class.java, vo)
        areaAppService.saveLevelInfo(dto)
    }

}