package com.edu.book.domain.wechat.dto

import java.io.Serializable
import javax.validation.constraints.NotBlank

/**
 * @Auther: liukaihua
 * @Date: 2024/9/18 19:29
 * @Description:
 */
class WechatCheckMessageDto: Serializable {

    @NotBlank(message = "token不能为空")
    var accessToken: String = ""

    @NotBlank(message = "文本不能为空")
    var content: String = ""

    @NotBlank(message = "openid不能为空")
    var openId: String = ""

}