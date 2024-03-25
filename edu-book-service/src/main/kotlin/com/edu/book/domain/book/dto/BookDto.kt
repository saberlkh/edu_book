package com.edu.book.domain.book.dto

import java.io.Serializable
import java.util.*

/**
 * @Auther: liukaihua
 * @Date: 2024/3/25 09:30
 * @Description:
 */
class BookDto: Serializable {

    /**
     * 业务唯一id
     */
    var uid: String? = null

    /**
     * isbn
     */
    var isbnCode: String? = null

    /**
     * 标题
     */
    var title: String? = null

    /**
     * 副标题
     */
    var subTitle: String? = null

    /**
     * 图片地址
     */
    var picUrl: String? = null

    /**
     * 作者
     */
    var author: String? = null

    /**
     * 简介
     */
    var summary: String? = null

    /**
     * 出版社
     */
    var publisher: String? = null

    /**
     * 发布地方
     */
    var publicPlace: String? = null

    /**
     * 发布时间
     */
    var publicDate: Date? = null

    /**
     * 页数
     */
    var page: Int? = null

    /**
     * 价格 单位分
     */
    var price: Int? = null

    /**
     * 装订方式
     */
    var binding: String? = null

    /**
     * isbn10位
     */
    var isbn10Code: String? = null

    /**
     * 主题词
     */
    var keyword: String? = null

    /**
     * cip核准号
     */
    var cip: String? = null

    /**
     * 版次
     */
    var edition: String? = null

    /**
     * 印次
     */
    var impression: String? = null

    /**
     * 正文语种
     */
    var language: String? = null

    /**
     * 开本
     */
    var format: String? = null

    /**
     * 中图法分类
     */
    var bookClass: String? = null

    /**
     * 销售凭条
     */
    var sellList: List<BookSellDto> = emptyList()

}