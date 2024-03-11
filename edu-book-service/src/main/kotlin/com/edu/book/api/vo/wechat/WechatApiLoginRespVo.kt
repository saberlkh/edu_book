package com.edu.book.api.vo.wechat

import java.io.Serializable

/**
 * @Auther: liukaihua
 * @Date: 2024/3/11 19:30
 * @Description:
 */
class WechatApiLoginRespVo: Serializable {

    /**
     * session_key
     */
    var session_key: String? = ""

    /**
     * unionid
     */
    var unionid: String? = ""

    /**
     * errmsg
     */
    var errmsg: String? = ""

    /**
     * openid
     */
    var openid: String? = ""

    /**
     * errcode
     */
    var errcode: Int? = null

}