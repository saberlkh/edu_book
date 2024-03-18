package com.edu.book.api.vo.user

import java.io.Serializable
import javax.validation.constraints.NotBlank

/**
 * @Auther: liukaihua
 * @Date: 2024/3/18 16:29
 * @Description:
 */
class UnbindAccountVo: Serializable {

    /**
     * openid
     */
    @NotBlank
    var openId: String = ""

    /**
     * 手机号
     */
    @NotBlank
    var phone: String = ""

}