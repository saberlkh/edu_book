package com.edu.book.domain.hair.dto

import java.io.Serializable

/**
 * @Auther: liukaihua
 * @Date: 2024/4/13 17:10
 * @Description:
 */
class SaveHairClassifyDto: Serializable {

    /**
     * 分类名
     */
    var classifyName: String = ""

    /**
     * 分类封面
     */
    var classifyCoverUrl: String = ""

    /**
     * 文件
     */
    var files: List<HairClassifyFileDto> = emptyList()

}