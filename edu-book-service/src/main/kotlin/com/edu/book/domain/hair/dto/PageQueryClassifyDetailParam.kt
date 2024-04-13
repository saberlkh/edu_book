package com.edu.book.domain.hair.dto

import java.io.Serializable
import org.apache.commons.lang3.math.NumberUtils

/**
 * @Auther: liukaihua
 * @Date: 2024/4/13 23:41
 * @Description:
 */
class PageQueryClassifyDetailParam: Serializable {

    var classifyUid: String = ""

    var page: Int = NumberUtils.INTEGER_ONE

    var pageSize: Int = 10

}