package com.edu.book.infrastructure.dto.wechat

import java.io.Serializable
import org.apache.commons.lang3.math.NumberUtils

/**
 * @Auther: liukaihua
 * @Date: 2024/3/11 22:33
 * @Description:
 */
class WechatGetAccessTokenApiDto: Serializable {

    /**
     * access_token
     */
    var access_token: String = ""

    /**
     * expires_in
     */
    var expires_in: Long = NumberUtils.LONG_ZERO

}