package com.edu.book.infrastructure.util

import com.alibaba.fastjson.JSON
import com.edu.book.domain.upload.dto.UploadFileDto
import com.edu.book.domain.upload.dto.UploadFileRespDto
import com.edu.book.domain.upload.service.UploadFileDomainService
import com.edu.book.infrastructure.config.SystemConfig
import com.edu.book.infrastructure.enums.FileTypeEnum
import com.qiniu.storage.BucketManager
import java.io.FileInputStream
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.qiniu.storage.Configuration
import com.qiniu.storage.Region
import com.qiniu.storage.UploadManager
import com.qiniu.storage.model.DefaultPutRet
import com.qiniu.util.Auth
import org.apache.commons.lang3.StringUtils
import org.slf4j.LoggerFactory

/**
 * @Auther: liukaihua
 * @Date: 2024/3/31 21:32
 * @Description:
 */

@Component
class QiNiuUtil {

    private val logger = LoggerFactory.getLogger(QiNiuUtil::class.java)

    @Autowired
    private lateinit var systemConfig: SystemConfig

    @Autowired
    private lateinit var uploadFileDomainService: UploadFileDomainService

    /**
     * 删除文件
     */
    fun delete(fileKeys: List<String>) {
        if (fileKeys.isNullOrEmpty()) return
        val configuration = Configuration(Region.region2())
        val auth = Auth.create(systemConfig.qiniuAccessKey, systemConfig.qiniuSecretKey)
        val bucketManager = BucketManager(auth, configuration)
        val batchOperations = BucketManager.BatchOperations()
        fileKeys.map {
            batchOperations.addDeleteOp(systemConfig.qiniuBucketName, it)
        }
        val response = bucketManager.batch(batchOperations)
        //删除数据库
        uploadFileDomainService.batchRemove(fileKeys)
        logger.info("删除文件-成功删除文件集合: ${JSON.toJSONString(response)}")
    }

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
        //插入数据
        val filePath = path + putRet.key
        val videoCoverUrl = if (StringUtils.equals(fileType, FileTypeEnum.VIDEO.fileType)) {
            "$filePath?vframe/jpg/offset/1"
        } else {
            ""
        }
        val fileUploadDto = UploadFileDto.buildUploadFileDto(
            filePath, putRet.key, fileType, systemConfig.qiniuBucketName, videoCoverUrl
        )
        uploadFileDomainService.saveUploadFileRecord(fileUploadDto)
        return UploadFileRespDto().apply {
            this.filePath = filePath
            this.fileKey = putRet.key
            this.videoCoverUrl = videoCoverUrl
            this.fileType = fileType
        }
    }

}