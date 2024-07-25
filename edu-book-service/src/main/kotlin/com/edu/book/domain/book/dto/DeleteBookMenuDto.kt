package com.edu.book.domain.book.dto

import java.io.Serializable
import javax.validation.constraints.NotBlank

/**
 * @Auther: liukaihua
 * @Date: 2024/7/24 20:55
 * @Description:
 */
class DeleteBookMenuDto: Serializable {

    /**
     * 图书Uid
     */
    @NotBlank(message = "图书uid不能为空")
    var bookUid: String = ""

}