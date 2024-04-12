package com.edu.book.domain.upload.service

import com.edu.book.domain.upload.dto.UploadFileDto
import com.edu.book.domain.upload.repository.UploadFileRepository
import com.edu.book.infrastructure.po.upload.UploadFilePo
import com.edu.book.infrastructure.util.MapperUtil
import com.edu.book.infrastructure.util.UUIDUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @Auther: liukaihua
 * @Date: 2024/4/11 22:23
 * @Description:
 */

@Service
class UploadFileDomainService {

    @Autowired
    private lateinit var uploadFileRepository: UploadFileRepository

    /**
     * 添加上传文件记录
     */
    fun saveUploadFileRecord(dto: UploadFileDto) {
        //判断fileKey是否存在
        val currentFilePo = uploadFileRepository.queryUploadFileByFileKey(dto.fileKey!!)
        if (currentFilePo != null) {
            return
        }
        val po = MapperUtil.map(UploadFilePo::class.java, dto).apply {
            this.uid = UUIDUtil.createUUID()
        }
        uploadFileRepository.save(po)
    }

}