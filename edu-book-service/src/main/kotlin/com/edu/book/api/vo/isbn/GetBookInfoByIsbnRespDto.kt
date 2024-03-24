package com.edu.book.api.vo.isbn

import java.io.Serializable

/**
 * @Auther: liukaihua
 * @Date: 2024/3/24 23:01
 * @Description:
 */
class GetBookInfoByIsbnRespDto: Serializable {

    /**
     * status
     */
    var status: String? = null

    /**
     * msg
     */
    var msg: String? = null

    /**
     * result
     */
    var result: IsbnBookInfoRespDto? = null

}

class IsbnBookInfoRespDto: Serializable {

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
     * pubplace
     */
    var pubplace: String? = null

    /**
     * pubdate
     */
    var pubdate: String? = null

    /**
     * page
     */
    var page: String? = null

    /**
     * price
     */
    var price: String? = null

    /**
     * binding
     */
    var binding: String? = null

    /**
     * isbn
     */
    var isbn: String? = null

    /**
     * isbn10
     */
    var isbn10: String? = null

    /**
     * keyword
     */
    var keyword: String? = null

    /**
     * cip
     */
    var cip: String? = null

    /**
     * edition
     */
    var edition: String? = null

    /**
     * impression
     */
    var impression: String? = null

    /**
     * language
     */
    var language: String? = null

    /**
     * format
     */
    var format: String? = null

    /**
     * class
     */
    var `class`: String? = null

    /**
     * sellerlist
     */
    var sellerlist: List<IsbnBookSellInfoRespDto>? = emptyList()

}

class IsbnBookSellInfoRespDto: Serializable {

    /**
     * seller
     */
    var seller: String? = null

    /**
     * price
     */
    var price: String? = null

    /**
     * link
     */
    var link: String? = null

}