package com.edu.book.api.http.controller.upload

import com.edu.book.api.http.service.UploadWebService
import com.edu.book.infrastructure.anno.Response
import com.edu.book.infrastructure.enums.FileTypeEnum
import com.edu.book.infrastructure.response.ResponseVo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

/**
 * @Auther: liukaihua
 * @Date: 2024/3/31 21:53
 * @Description:
 */

@RestController
@RequestMapping("/upload")
@Response
class UploadController {

    @Autowired
    private lateinit var uploadWebService: UploadWebService

    /**
     * 上传图片
     */
    @PostMapping("/v1/image")
    fun uploadImage(file: MultipartFile): ResponseVo<String> {
        return ResponseVo(uploadWebService.upload(file, FileTypeEnum.IMAGE.fileType))
    }

}