package com.edu.book.api.vo.read

import java.io.Serializable
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

/**
 * @Auther: liukaihua
 * @Date: 2024/6/4 19:32
 * @Description:
 */
class PublishReadCircleVo: Serializable {

    /**
     * 文本
     */
    @NotBlank(message = "文本不能为空")
    var readText: String = ""

    /**
     * 附件
     */
    @NotEmpty(message = "附件不能为空")
    var attachments: List<String> = emptyList()

}