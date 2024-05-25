package com.edu.book.api.vo.book

import java.io.Serializable
import org.apache.commons.lang3.math.NumberUtils

/**
 * @Auther: liukaihua
 * @Date: 2024/5/25 21:54
 * @Description:
 */
class PageQueryUserBookCollectParamVo: Serializable {

    /**
     * 用户Uid
     */
    var userUid: String = ""

    /**
     * page
     */
    var page: Int = NumberUtils.INTEGER_ONE

    /**
     * pageSize
     */
    var pageSize: Int = 10

}