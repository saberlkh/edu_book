package com.edu.book.api.http.controller.area

import com.edu.book.api.http.service.AreaWebService
import com.edu.book.api.vo.area.QueryAreaInfoVo
import com.edu.book.infrastructure.anno.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * @Auther: liukaihua
 * @Date: 2024/5/8 20:30
 * @Description:
 */

@RestController
@RequestMapping("/area")
@Response
class AreaController {

    @Autowired
    private lateinit var areaWebService: AreaWebService

    /**
     * 查询地区列表
     */
    @GetMapping("/v1/area")
    fun queryAreaInfo(@RequestParam(required = false) areaCode: String?): List<QueryAreaInfoVo> {
        return areaWebService.queryAreaInfo(areaCode)
    }

}