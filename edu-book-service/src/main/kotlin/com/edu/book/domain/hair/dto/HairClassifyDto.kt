package com.edu.book.domain.hair.dto

import java.io.Serializable

/**
 * @Auther: liukaihua
 * @Date: 2024/4/13 16:53
 * @Description:
 */
class HairClassifyDto: Serializable {

    /**
     * 分类名称
     */
    var classifyName: String? = null

    /**
     * 分类封面
     */
    var classifyCoverUrl: String? = null

}