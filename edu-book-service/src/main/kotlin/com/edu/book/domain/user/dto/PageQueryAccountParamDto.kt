package com.edu.book.domain.user.dto

import java.io.Serializable
import org.apache.commons.lang3.math.NumberUtils

/**
 * @Auther: liukaihua
 * @Date: 2024/5/13 19:49
 * @Description:
 */
class PageQueryAccountParamDto: Serializable {

    /**
     * page
     */
    var page: Int = NumberUtils.INTEGER_ONE

    /**
     * pageSize
     */
    var pageSize: Int = 10

    /**
     * 班级uid
     */
    var classUid: String = ""

}