package com.edu.book.infrastructure.dto.wechat

import java.io.Serializable
import org.apache.commons.lang3.math.NumberUtils

/**
 * @Auther: liukaihua
 * @Date: 2024/3/13 09:34
 * @Description:
 */
class WechatGetPhoneApiDto: Serializable {

    /**
     * errcode
     */
    var errcode: Int? = NumberUtils.INTEGER_ZERO

    /**
     * errmsg
     */
    var errmsg: String? = ""

    /**
     * phone_info
     */
    var phone_info: WechatPhoneInfoDto? = null

}

class WechatPhoneInfoDto: Serializable {

    /**
     * phoneNumber
     */
    var phoneNumber: String = ""

    /**
     * purePhoneNumber
     */
    var purePhoneNumber: String = ""

    /**
     * countryCode
     */
    var countryCode: String = ""

    /**
     * watermark
     */
    var watermark: WechatWatermarkDto? = null

}

class WechatWatermarkDto: Serializable {

    /**
     * timestamp
     */
    var timestamp: Long = NumberUtils.LONG_ZERO

    /**
     * appid
     */
    var appid: String = ""

}