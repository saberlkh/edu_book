package com.edu.book.domain.area.service

import com.edu.book.domain.area.dto.QueryAreaInfoDto
import com.edu.book.domain.area.enums.AreaTypeEnum
import com.edu.book.domain.area.repository.AreaRepository
import com.edu.book.infrastructure.util.MapperUtil
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

    /**
     * 查询地区信息
     */
    fun queryAreaInfo(areaCode: String?): List<QueryAreaInfoDto> {
        val areaType = if (areaCode.isNullOrBlank()) {
            AreaTypeEnum.PROVINCE.type
        } else {
            null
        }
        val pos = areaRepository.queryByAreaType(areaCode, areaType) ?: return emptyList()
        return MapperUtil.mapToList(QueryAreaInfoDto::class.java, pos)
    }

}