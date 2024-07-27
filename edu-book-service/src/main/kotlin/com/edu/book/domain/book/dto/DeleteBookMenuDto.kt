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
     * isbn
     */
    @NotBlank(message = "isbn不能为空")
    var isbn: String = ""

}