package com.edu.book.domain.hair.dto

import org.apache.commons.lang3.math.NumberUtils

/**
 * @Auther: liukaihua
 * @Date: 2024/4/16 22:12
 * @Description:
 */
class ModifyClassifyDto {

    /**
     * 分类
     */
    var classifyUid: String = ""

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

    /**
     * 排序
     */
    var sort: Int? = NumberUtils.INTEGER_ZERO

}