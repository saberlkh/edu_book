package com.edu.book.api.vo.book

import java.io.Serializable

/**
 * @Auther: liukaihua
 * @Date: 2024/5/25 21:53
 * @Description:
 */
class PageQueryBookCollectVo: Serializable {

    /**
     * 图书自编码
     */
    var bookUid: String = ""

    /**
     * 图片地址
     */
    var pic: String? = null

    /**
     * 标题
     */
    var title: String? = null

    /**
     * 作者
     */
    var author: String? = null

    /**
     * 简介
     */
    var summary: String? = null

    /**
     * subtitle
     */
    var subtitle: String? = null

}