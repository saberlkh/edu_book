package com.edu.book.domain.book.dto

import java.io.Serializable

/**
 * @Auther: liukaihua
 * @Date: 2024/5/25 21:47
 * @Description:
 */
class PageQueryBookCollectDto: Serializable {

    /**
     * 图书自编码
     */
    var bookUid: String = ""

    /**
     * 图片地址
     */
    var picUrl: String? = null

    /**
     * 标题
     */
    var title: String? = null

    /**
     * 子标题
     */
    var subTitle: String? = null

    /**
     * 作者
     */
    var author: String? = null

    /**
     * 简介
     */
    var summary: String? = null

    /**
     * 用户
     */
    var userUid: String? = null

}