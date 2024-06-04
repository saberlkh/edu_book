package com.edu.book.api.vo.read

import java.io.Serializable

/**
 * @Auther: liukaihua
 * @Date: 2024/6/4 23:37
 * @Description:
 */
class ReadCircleAttachmentVo: Serializable {

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