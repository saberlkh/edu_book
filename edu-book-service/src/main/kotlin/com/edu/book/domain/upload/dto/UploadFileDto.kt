package com.edu.book.domain.upload.dto

import java.io.Serializable

/**
 * @Auther: liukaihua
 * @Date: 2024/4/11 22:21
 * @Description:
 */
class UploadFileDto: Serializable {

    /**
     * 业务唯一id
     */
    var uid: String? = null

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
     * 存储桶名称
     */
    var bucketName: String? = null

    /**
     * 视频封面地址
     */
    var videoCoverUrl: String? = null

}