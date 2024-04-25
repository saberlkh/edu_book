package com.edu.book.api.http.service

import com.edu.book.api.vo.upload.UploadFileVo
import com.edu.book.infrastructure.enums.CommonFileTypeEnum
import com.edu.book.infrastructure.enums.ErrorCodeConfig
import com.edu.book.infrastructure.enums.FileTypeEnum
import com.edu.book.infrastructure.exception.WebAppException
import com.edu.book.infrastructure.util.FileTypeUtil
import com.edu.book.infrastructure.util.MapperUtil
import com.edu.book.infrastructure.util.QiNiuUtil
import java.io.File
import java.io.FileInputStream
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.FutureTask
import net.coobird.thumbnailator.Thumbnails
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

/**
 * @Auther: liukaihua
 * @Date: 2024/3/31 21:48
 * @Description:
 */

@Service
class UploadWebService {

    private val logger = LoggerFactory.getLogger(UploadWebService::class.java)

    @Autowired
    private lateinit var qiNiuUtil: QiNiuUtil

    companion object {

        const val UPLOAD_MAX_NUM = 8

    }

    /**
     * 批量上传
     */
    fun batchUpload(files: List<MultipartFile>): List<UploadFileVo> {
        //个数不能大于8
        if (files.size > UPLOAD_MAX_NUM) throw WebAppException(ErrorCodeConfig.UPLOAD_MAX_NUM)
        //循环调用
        val futureTaskList = mutableListOf<FutureTask<UploadFileVo?>>()
        val threadPool = Executors.newFixedThreadPool(files.size)
        files.mapNotNull { file ->
            val callTask = Callable {
                try {
                    upload(file)
                } catch (e: Exception) {
                    logger.error("上传文件失败-throw error:${e.localizedMessage}, 文件名:${file.originalFilename}")
                    null
                }
            }
            val futureTask = FutureTask(callTask)
            futureTaskList.add(futureTask)
            threadPool.submit(futureTask)
        }
        //判断结果拿到返回值
        val result = futureTaskList.mapNotNull {
            try {
                it.get()
            } catch (e: Exception) {
                logger.error("通过get方法获取返回结果-失败 throw error:${e.message}")
                null
            }
        }
        threadPool.shutdown()
        return result
    }

    fun upload(file: MultipartFile): UploadFileVo {
        val fileName = file.originalFilename
        if (fileName.isNullOrBlank()) {
            throw WebAppException(ErrorCodeConfig.FILE_NAME_NOT_NULL)
        }
        //判断文件类型，如果是图片进行压缩
        val commonFileType = FileTypeUtil.getFileType(file.inputStream as FileInputStream)
        var finalFile: File? = null
        var fileType = FileTypeEnum.IMAGE.fileType
        val fileInputStream = if (CommonFileTypeEnum.imageFile(commonFileType)) {
            val path = System.getProperty("user.dir")
            finalFile = File(path + file.originalFilename)
            //进行图片压缩
            Thumbnails.of(file.inputStream)
                .scale(1.00)
                .outputQuality(0.5)
                .toFile(finalFile)
            FileInputStream(finalFile)
        } else {
            fileType = FileTypeEnum.VIDEO.fileType
            file.inputStream as FileInputStream
        }
        val dto = qiNiuUtil.upload(fileInputStream, fileType)
        fileInputStream?.close()
        finalFile?.delete()
        return MapperUtil.map(UploadFileVo::class.java, dto)
    }

    /**
     * 验证文件名称：仅包含 汉字、字母、数字、下划线和点号
     *
     * @param fileName 文件名称
     * @return 返回true表示符合要求
     */
    fun validateFileName(fileName: String): Boolean {
        val regex = Regex("^[a-zA-Z0-9_\\u4e00-\\u9fa5_\\.]+$")
        return fileName.matches(regex)
    }

}