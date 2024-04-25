package com.edu.book.domain.upload.service

import com.edu.book.domain.upload.dto.UploadFileDto
import com.edu.book.domain.upload.repository.UploadFileRepository
import com.edu.book.domain.user.exception.ConcurrentCreateInteractRoomException
import com.edu.book.infrastructure.config.SystemConfig
import com.edu.book.infrastructure.constants.RedisKeyConstant.UPLOAD_FILE_KEY
import com.edu.book.infrastructure.po.upload.UploadFilePo
import com.edu.book.infrastructure.util.MapperUtil
import com.edu.book.infrastructure.util.UUIDUtil
import java.util.concurrent.TimeUnit
import org.redisson.api.RedissonClient
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

    @Autowired
    private lateinit var redissonClient: RedissonClient

    @Autowired
    private lateinit var systemConfig: SystemConfig

    /**
     * 添加上传文件记录
     */
    fun saveUploadFileRecord(dto: UploadFileDto) {
        val lockKey = UPLOAD_FILE_KEY + dto.fileKey
        val lock = redissonClient.getLock(lockKey)
        try {
            if (!lock.tryLock(systemConfig.distributedLockWaitTime, systemConfig.distributedLockReleaseTime, TimeUnit.MILLISECONDS)) {
                throw ConcurrentCreateInteractRoomException(dto.fileKey!!)
            }
            //判断fileKey是否存在
            val currentFilePo = uploadFileRepository.queryUploadFileByFileKey(dto.fileKey!!)
            if (currentFilePo != null) {
                return
            }
            val po = MapperUtil.map(UploadFilePo::class.java, dto).apply {
                this.uid = UUIDUtil.createUUID()
            }
            uploadFileRepository.save(po)
        } finally {
            if (lock.isHeldByCurrentThread) {
                lock.unlock()
            }
        }
    }

}