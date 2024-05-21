package com.edu.book.api.vo.book

import java.io.Serializable
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

/**
 * @Auther: liukaihua
 * @Date: 2024/3/25 20:04
 * @Description:
 */
class ScanBookCodeInStorageVo: Serializable {

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
     * isbn
     */
    @NotBlank(message = "isbn不能为空")
    var isbn: String = ""

    /**
     * 图书自编码
     */
    @NotBlank(message = "图书自编码不能为空")
    var bookUid: String = ""

    /**
     * 园区
     */
    @NotBlank(message = "园区不能为空")
    var gardenUid: String = ""

    /**
     * 分类
     */
    @NotEmpty(message = "分类不能为空")
    var classifyList: List<String> = emptyList()

    /**
     * 年龄段
     */
    @NotEmpty(message = "年龄段不能为空")
    var ageGroups: List<Int> = emptyList()

}