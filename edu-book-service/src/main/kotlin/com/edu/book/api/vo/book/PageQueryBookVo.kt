package com.edu.book.api.vo.book

import java.io.Serializable
import org.apache.commons.lang3.math.NumberUtils

/**
 * @Auther: liukaihua
 * @Date: 2024/3/27 23:15
 * @Description:
 */
class PageQueryBookVo: Serializable {

    /**
     * 标题
     */
    var title: String? = null

    var subTitle: String? = null

    var isbn: String? = null

    var bookUid: String? = null

    var classifyList: List<String> = emptyList()

    var ageGroups: List<Int> = emptyList()

    var page: Int = NumberUtils.INTEGER_ONE

    var pageSize: Int = 10

    var sort: String = ""

    var orderBy = ""

}