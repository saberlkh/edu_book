package com.edu.book.api.vo.book

import java.io.Serializable
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

/**
 * @Auther: liukaihua
 * @Date: 2024/5/25 21:29
 * @Description:
 */
class BookCollectVo: Serializable {

    /**
     * 状态
     */
    @NotNull(message = "状态不能为空")
    var collectStatus: Int? = null

    /**
     * 图书Uid
     */
    @NotBlank(message = "图书uid不能为空")
    var bookUid: String = ""

}