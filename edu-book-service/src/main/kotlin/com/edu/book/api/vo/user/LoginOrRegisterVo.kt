package com.edu.book.api.vo.user

import java.io.Serializable
import javax.validation.constraints.NotBlank

/**
 * @Auther: liukaihua
 * @Date: 2024/3/13 23:55
 * @Description:
 */
class LoginOrRegisterVo: Serializable {

    /**
     * open
     */
    @NotBlank(message = "openId不能为空")
    var openId: String = ""

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    var phone: String = ""

}