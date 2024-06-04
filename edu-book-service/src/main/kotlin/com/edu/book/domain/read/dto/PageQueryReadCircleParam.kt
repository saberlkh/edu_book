package com.edu.book.domain.read.dto

import java.io.Serializable
import org.apache.commons.lang3.math.NumberUtils

/**
 * @Auther: liukaihua
 * @Date: 2024/6/4 19:48
 * @Description:
 */
class PageQueryReadCircleParam: Serializable {

    /**
     * 园区Uid
     */
    var gardenUid: String = ""

    /**
     * page
     */
    var page: Int = NumberUtils.INTEGER_ONE

    /**
     * pageSize
     */
    var pageSize: Int = 10

}