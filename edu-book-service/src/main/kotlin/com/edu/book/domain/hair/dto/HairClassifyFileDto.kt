package com.edu.book.domain.hair.dto

import java.io.Serializable

/**
 * @Auther: liukaihua
 * @Date: 2024/4/13 16:52
 * @Description:
 */
class HairClassifyFileDto: Serializable {

    /**
     * 分类uid
     */
    var classifyUid: String? = null

    /**
     * 文件路径
     */
    var filePath: String? = null

    /**
     * 文件名称
     */
    var fileKey: String? = null

    /**
     * 文件类型
     */
    var fileType: String? = null

    /**
     * 视频封面
     */
    var videoCoverUrl: String? = null

}