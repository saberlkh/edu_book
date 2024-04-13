package com.edu.book.api.vo.hair

import java.io.Serializable

/**
 * @Auther: liukaihua
 * @Date: 2024/4/13 17:18
 * @Description:
 */
class HairClassifyFileVo: Serializable {

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

}