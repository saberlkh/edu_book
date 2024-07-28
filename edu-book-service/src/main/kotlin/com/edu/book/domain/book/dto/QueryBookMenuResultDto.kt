package com.edu.book.domain.book.dto

import java.io.Serializable

/**
 * @Auther: liukaihua
 * @Date: 2024/7/27 23:07
 * @Description:
 */
class QueryBookMenuResultDto: Serializable {

    /**
     * title
     */
    var title: String? = null

    /**
     * subtitle
     */
    var subtitle: String? = null

    /**
     * pic
     */
    var pic: String? = null

    /**
     * author
     */
    var author: String? = null

    /**
     * summary
     */
    var summary: String? = null

    /**
     * publisher
     */
    var publisher: String? = null

    /**
     * isbn
     */
    var isbn: String = ""

}