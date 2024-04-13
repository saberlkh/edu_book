package com.edu.book.application.service.hair

import com.edu.book.domain.hair.dto.PageQueryClassifyDetailParam
import com.edu.book.domain.hair.dto.PageQueryHairDetailDto
import com.edu.book.domain.hair.dto.SaveHairClassifyDto
import com.edu.book.domain.hair.service.HairDomainService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @Auther: liukaihua
 * @Date: 2024/4/13 17:17
 * @Description:
 */

@Service
class HairAppService {

    @Autowired
    private lateinit var hairDomainService: HairDomainService

    /**
     * 添加分类
     */
    fun saveHairClassify(dto: SaveHairClassifyDto): String {
        return hairDomainService.saveHairClassify(dto)
    }

    /**
     * 分页查询
     */
    fun pageQueryClassifyDetail(param: PageQueryClassifyDetailParam): PageQueryHairDetailDto {
        return hairDomainService.pageQueryClassifyDetail(param)
    }

    /**
     * 删除
     */
    fun deleteClassify(classifyUid: String) {
        hairDomainService.deleteClassify(classifyUid)
    }

}