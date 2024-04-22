package com.edu.book.domain.upload.dto

import java.io.Serializable

/**
 * @Auther: liukaihua
 * @Date: 2024/4/12 22:56
 * @Description:
 */
class UploadFileRespDto: Serializable {

    var fileKey: String = ""

    var filePath: String = ""

    /**
     * 视频封面
     */
    var videoCoverUrl: String? = null

    var fileType: String? = null

}