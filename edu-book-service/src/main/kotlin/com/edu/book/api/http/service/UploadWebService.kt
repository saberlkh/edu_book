package com.edu.book.api.http.service

import com.edu.book.infrastructure.enums.ErrorCodeConfig
import com.edu.book.infrastructure.exception.WebAppException
import com.edu.book.infrastructure.util.QiNiuUtil
import java.io.FileInputStream
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

    @Autowired
    private lateinit var qiNiuUtil: QiNiuUtil

    fun upload(file: MultipartFile, fileType: String): String {
        val fileName = file.originalFilename
        if (fileName.isNullOrBlank()) {
            throw WebAppException(ErrorCodeConfig.FILE_NAME_NOT_NULL)
        }
        val fileInputStream = file.inputStream as FileInputStream
        val url = qiNiuUtil.upload(fileInputStream, fileType)
        return url
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