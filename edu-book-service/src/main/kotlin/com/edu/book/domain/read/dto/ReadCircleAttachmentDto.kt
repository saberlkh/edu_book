package com.edu.book.domain.read.dto

import java.io.Serializable

/**
 * @Auther: liukaihua
 * @Date: 2024/6/4 22:13
 * @Description:
 */
class ReadCircleAttachmentDto: Serializable {

    /**
     * filekey
     */
    var fileKey: String = ""

    /**
     * 下载地址
     */
    var downloadUrl: String = ""

    /**
     * 文件类型
     */
    var fileType: String = ""

}