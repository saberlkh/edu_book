package com.edu.book.api.vo.hair

import java.io.Serializable
import javax.validation.constraints.NotBlank

/**
 * @Auther: liukaihua
 * @Date: 2024/4/13 17:18
 * @Description:
 */
class SaveHairClassifyVo: Serializable {

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

}