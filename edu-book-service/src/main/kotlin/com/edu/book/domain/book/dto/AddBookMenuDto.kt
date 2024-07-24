package com.edu.book.domain.book.dto

import java.io.Serializable
import javax.validation.constraints.NotBlank

/**
 * @Auther: liukaihua
 * @Date: 2024/7/24 20:36
 * @Description:
 */
class AddBookMenuDto: Serializable {

    /**
     * 图书Uid
     */
    @NotBlank(message = "图书uid不能为空")
    var bookUid: String = ""

}