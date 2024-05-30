package com.edu.book.api.vo.book

import java.io.Serializable
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

/**
 * @Auther: liukaihua
 * @Date: 2024/5/30 19:29
 * @Description:
 */
class ReturnBookVo: Serializable {

    /**
     * Uid
     */
    @NotBlank(message = "uid不能为空")
    var bookUid: String = ""

    /**
     * 是否损耗
     */
    @NotNull(message = "状态不能为空")
    var loss: Boolean = false

}