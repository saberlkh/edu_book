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
    @NotBlank(message = "openId不能为空")
    var openId: String = ""

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    var phone: String = ""

    /**
     * 借阅卡Id
     */
    @NotBlank(message = "借书卡Id不能为空")
    var borrowCardId: String = ""

    /**
     * unionid
     */
    var unionId: String = ""

}