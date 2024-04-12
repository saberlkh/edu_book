package com.edu.book.infrastructure.util

import com.alibaba.fastjson.JSON
import com.edu.book.domain.upload.dto.UploadFileRespDto
import com.edu.book.infrastructure.config.SystemConfig
import java.io.FileInputStream
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.qiniu.storage.Configuration
import com.qiniu.storage.Region
import com.qiniu.storage.UploadManager
import com.qiniu.storage.model.DefaultPutRet
import com.qiniu.util.Auth

/**
 * @Auther: liukaihua
 * @Date: 2024/3/31 21:32
 * @Description:
 */

@Component
class QiNiuUtil {

    @Autowired
    private lateinit var systemConfig: SystemConfig

    /**
     * 上传文件
     */
    fun upload(file: FileInputStream, fileType: String): UploadFileRespDto {
        val configuration = Configuration(Region.region2())
        val uploadManager = UploadManager(configuration)
        val auth = Auth.create(systemConfig.qiniuAccessKey, systemConfig.qiniuSecretKey)
        val path = systemConfig.qiniuUploadDomain
        val uploadToken = auth.uploadToken(systemConfig.qiniuBucketName)
        val response = uploadManager.put(file, null, uploadToken, null, null)
        val putRet = JSON.parseObject(response.bodyString(), DefaultPutRet::class.java)
        return UploadFileRespDto().apply {
            this.downloadUrl = path + putRet.key
            this.fileKey = putRet.key
        }
    }

}