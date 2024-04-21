package com.edu.book.api.vo.hair

import java.io.Serializable
import javax.validation.constraints.NotBlank
import org.apache.commons.lang3.math.NumberUtils

/**
 * @Auther: liukaihua
 * @Date: 2024/4/16 22:30
 * @Description:
 */
class ModifyHairClassifyVo: Serializable {

    /**
     * 分类名
     */
    @NotBlank(message = "分类uid不能为空")
    var classifyUid: String = ""

    /**
     * 分类名
     */
    @NotBlank(message = "分类名不能为空")
    var classifyName: String = ""

    /**
     * 分类封面
     */
    @NotBlank(message = "分类封面不能为空")
    var classifyCoverUrl: String = ""

    /**
     * 文件
     */
    var files: List<HairClassifyFileVo> = emptyList()

    /**
     * 排序
     */
    var sort: Int? = NumberUtils.INTEGER_ZERO

}