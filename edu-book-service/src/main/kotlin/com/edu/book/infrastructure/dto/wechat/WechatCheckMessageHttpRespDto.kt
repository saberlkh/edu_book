package com.edu.book.infrastructure.dto.wechat

import java.io.Serializable

/**
 * @Auther: liukaihua
 * @Date: 2024/9/18 19:40
 * @Description:
 */
class WechatCheckMessageHttpRespDto: Serializable {

    var result: CheckMessageResponseResultDto? = null

    var detail: List<CheckMessageResponseDetailDto>? = emptyList()

}