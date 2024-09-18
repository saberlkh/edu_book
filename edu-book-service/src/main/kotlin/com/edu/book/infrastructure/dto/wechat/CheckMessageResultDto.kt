package com.edu.book.infrastructure.dto.wechat

import java.io.Serializable
import org.apache.commons.lang3.math.NumberUtils

/**
 * @Auther: liukaihua
 * @Date: 2024/9/18 19:35
 * @Description:
 */
class CheckMessageResultDto: Serializable {

    /**
     * errcode
     */
    var errcode: Int? = NumberUtils.INTEGER_ZERO

    /**
     * errmsg
     */
    var errmsg: String? = ""

    var trace_id: String? = ""

    var result: CheckMessageResponseResultDto? = null

    var detail: List<CheckMessageResponseDetailDto>? = emptyList()

}

class CheckMessageResponseDetailDto: Serializable {

    var strategy: String? = null

    var errcode: Int? = null

    var suggest: String? = null

    var label: Int? = null

    var keyword: String? = null

    var prob: Int? = null

}

class CheckMessageResponseResultDto: Serializable {

    var suggest: String? = null

    var label: Int? = null

}