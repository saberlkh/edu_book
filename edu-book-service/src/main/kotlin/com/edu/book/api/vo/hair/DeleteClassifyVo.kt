package com.edu.book.api.vo.hair

import java.io.Serializable
import javax.validation.constraints.NotBlank

/**
 * @Auther: liukaihua
 * @Date: 2024/4/13 23:08
 * @Description:
 */
class DeleteClassifyVo: Serializable {

    /**
     * 分类
     */
    @NotBlank
    var classifyUid: String = ""

}