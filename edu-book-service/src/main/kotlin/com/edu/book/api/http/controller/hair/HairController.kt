package com.edu.book.api.http.controller.hair

import com.edu.book.api.vo.hair.DeleteClassifyVo
import com.edu.book.api.vo.hair.ModifyHairClassifyVo
import com.edu.book.api.vo.hair.SaveHairClassifyVo
import com.edu.book.application.service.hair.HairAppService
import com.edu.book.domain.hair.dto.AdminLoginDto
import com.edu.book.domain.hair.dto.HairClassifyDto
import com.edu.book.domain.hair.dto.ModifyClassifyDto
import com.edu.book.domain.hair.dto.PageQueryClassifyDetailParam
import com.edu.book.domain.hair.dto.PageQueryHairDetailDto
import com.edu.book.domain.hair.dto.QueryClassifyListParam
import com.edu.book.domain.hair.dto.SaveHairClassifyDto
import com.edu.book.infrastructure.anno.Response
import com.edu.book.infrastructure.response.ResponseVo
import com.edu.book.infrastructure.util.MapperUtil
import javax.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @Auther: liukaihua
 * @Date: 2024/4/13 17:19
 * @Description:
 */

@RestController
@RequestMapping("/hair")
@Response
class HairController {

    @Autowired
    private lateinit var hairAppService: HairAppService

    /**
     * 添加分类
     */
    @PostMapping("/v1/classify")
    fun saveHairClassify(@RequestBody @Valid vo: SaveHairClassifyVo): ResponseVo<String> {
        val dto = MapperUtil.map(SaveHairClassifyDto::class.java, vo)
        return ResponseVo(hairAppService.saveHairClassify(dto))
    }

    /**
     * 删除
     */
    @DeleteMapping("/v1/classify")
    fun deleteClassify(@RequestBody @Valid vo: DeleteClassifyVo) {
        hairAppService.deleteClassify(vo.classifyUid)
    }

    /**
     * 更新
     */
    @PutMapping("/v1/classify")
    fun modifyClassify(@RequestBody @Valid vo: ModifyHairClassifyVo) {
        val dto = MapperUtil.map(ModifyClassifyDto::class.java, vo)
        hairAppService.modifyClassify(dto)
    }

    /**
     * 登录
     */
    @PostMapping("/v1/admin/login")
    fun modifyClassify(@RequestBody @Valid dto: AdminLoginDto): ResponseVo<String> {
        return ResponseVo(hairAppService.adminLogin(dto))
    }

    /**
     * 查询所有分类
     */
    @GetMapping("/v1/classify")
    fun queryAllClassify(param: QueryClassifyListParam): List<HairClassifyDto> {
        return hairAppService.queryAllClassify(param)
    }

    /**
     * 分页查询
     */
    @GetMapping("/v1/classify/detail")
    fun pageQueryClassifyDetail(param: PageQueryClassifyDetailParam): PageQueryHairDetailDto {
        return hairAppService.pageQueryClassifyDetail(param)
    }

}