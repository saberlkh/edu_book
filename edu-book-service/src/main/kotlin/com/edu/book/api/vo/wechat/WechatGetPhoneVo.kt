package com.edu.book.api.vo.wechat

import java.io.Serializable

/**
 * @Auther: liukaihua
 * @Date: 2024/3/13 10:38
 * @Description:
 */
class WechatGetPhoneVo: Serializable {

    /**
     * 手机号
     */
    var phone: String = ""

    /**
     * 区号
     */
    var countryCode: String = ""

}