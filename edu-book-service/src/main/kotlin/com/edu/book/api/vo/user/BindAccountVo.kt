package com.edu.book.api.vo.user

import java.io.Serializable
import javax.validation.constraints.NotBlank

/**
 * @Auther: liukaihua
 * @Date: 2024/3/15 10:13
 * @Description:
 */
class BindAccountVo: Serializable {

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

    /**
     * 借阅卡Id
     */
    @NotBlank
    var borrowCardId: String = ""

    /**
     * unionid
     */
    var unionId: String = ""

}