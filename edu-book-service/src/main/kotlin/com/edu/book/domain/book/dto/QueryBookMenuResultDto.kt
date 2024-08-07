package com.edu.book.domain.book.dto

import java.io.Serializable

/**
 * @Auther: liukaihua
 * @Date: 2024/7/27 23:07
 * @Description:
 */
class QueryBookMenuResultDto: Serializable {

    /**
     * 书单列表
     */
    var bookMenuUid: String = ""

    /**
     * 图片
     */
    var menuPic: String = ""

    /**
     * 描述
     */
    var menuDesc: String = ""

    /**
     * 标题
     */
    var menuTitle: String = ""

    /**
     * 园区Uid
     */
    var gardenUid: String = ""

    /**
     * 园区
     */
    var garden: String = ""

    /**
     * 幼儿园
     */
    var kindergartenUid: String = ""

    /**
     * 幼儿园
     */
    var kindergartenName: String = ""

    /**
     * 书籍列表
     */
    var books: List<BookMenuIsbnResultDto> = emptyList()

}

class BookMenuIsbnResultDto: Serializable {

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