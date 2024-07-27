package com.edu.book.domain.book.dto

import com.edu.book.infrastructure.constants.Constants.twenty
import java.io.Serializable
import org.apache.commons.lang3.math.NumberUtils

/**
 * @Auther: liukaihua
 * @Date: 2024/7/27 12:04
 * @Description:
 */
class PageQueryBookIsbnDto: Serializable {

    /**
     * 园区Uid
     */
    var gardenUid: String? = null

    /**
     * page
     */
    var page: Int = NumberUtils.INTEGER_ZERO

    /**
     * pagesize
     */
    var pageSize: Int = twenty

}