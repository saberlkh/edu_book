package com.edu.book.domain.hair.dto

import java.io.Serializable
import javax.validation.constraints.NotBlank

/**
 * @Auther: liukaihua
 * @Date: 2024/4/20 20:56
 * @Description:
 */
class AdminLoginDto: Serializable {

    @NotBlank
    var username: String = ""

    @NotBlank
    var password: String = ""

}