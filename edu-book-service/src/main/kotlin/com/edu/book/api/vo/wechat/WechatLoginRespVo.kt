package com.edu.book.api.vo.wechat

import java.io.Serializable

/**
 * @Auther: liukaihua
 * @Date: 2024/3/11 19:09
 * @Description:
 */
class WechatLoginRespVo: Serializable {

    /**
     * 会话密钥
     */
    var sessionKey: String = ""

    /**
     * 开放平台的唯一标识
     */
    var unionId: String = ""

    /**
     * 用户唯一标识
     */
    var openId: String = ""

}