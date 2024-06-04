package com.edu.book.domain.read.service

import com.edu.book.domain.area.enums.LevelTypeEnum
import com.edu.book.domain.area.repository.LevelRepository
import com.edu.book.domain.read.dto.PublishReadCircleDto
import com.edu.book.domain.read.mapper.ReadCircleEntityMapper.buildPublishBookReadCircleAttachment
import com.edu.book.domain.read.mapper.ReadCircleEntityMapper.buildPublishBookReadCirclePo
import com.edu.book.domain.read.repository.BookReadCircleAttachmentRepository
import com.edu.book.domain.read.repository.BookReadCircleCommentFlowRepository
import com.edu.book.domain.read.repository.BookReadCircleLikeFlowRepository
import com.edu.book.domain.read.repository.BookReadCircleRepository
import com.edu.book.domain.upload.repository.UploadFileRepository
import com.edu.book.domain.user.exception.AccountNotFoundException
import com.edu.book.domain.user.exception.ClassNotExistException
import com.edu.book.domain.user.exception.ConcurrentCreateInteractRoomException
import com.edu.book.domain.user.exception.UserNotFoundException
import com.edu.book.domain.user.repository.BookAccountRepository
import com.edu.book.domain.user.repository.BookUserRepository
import com.edu.book.infrastructure.config.SystemConfig
import com.edu.book.infrastructure.constants.RedisKeyConstant
import com.edu.book.infrastructure.util.UUIDUtil
import java.util.concurrent.TimeUnit
import javax.annotation.Resource
import org.redisson.api.RedissonClient
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @Auther: liukaihua
 * @Date: 2024/6/3 22:55
 * @Description:
 */

@Service
class BookReadCircleDomainService {

    private val logger = LoggerFactory.getLogger(BookReadCircleDomainService::class.java)

    @Autowired
    private lateinit var bookReadCircleAttachmentRepository: BookReadCircleAttachmentRepository

    @Autowired
    private lateinit var bookReadCircleRepository: BookReadCircleRepository

    @Autowired
    private lateinit var bookReadCircleLikeFlowRepository: BookReadCircleLikeFlowRepository

    @Autowired
    private lateinit var bookReadCircleCommentFlowRepository: BookReadCircleCommentFlowRepository

    @Autowired
    private lateinit var uploadFileRepository: UploadFileRepository

    @Autowired
    private lateinit var bookUserRepository: BookUserRepository

    @Autowired
    private lateinit var bookAccountRepository: BookAccountRepository

    @Resource
    private lateinit var redissonClient: RedissonClient

    @Autowired
    private lateinit var systemConfig: SystemConfig

    @Autowired
    private lateinit var levelRepository: LevelRepository

    /**
     * 发表阅读圈
     */
    @Transactional(rollbackFor = [Exception::class])
    fun publishReadCircle(dto: PublishReadCircleDto) {
        val lockKey = RedisKeyConstant.PUBLISH_READ_CIRCLE_LOCK_KEY + dto.userUid
        val lock = redissonClient.getLock(lockKey)
        try {
            if (!lock.tryLock(systemConfig.distributedLockWaitTime, systemConfig.distributedLockReleaseTime, TimeUnit.MILLISECONDS)) {
                throw ConcurrentCreateInteractRoomException(lockKey)
            }
            //查询用户信息
            val userPo = bookUserRepository.findByUserUid(dto.userUid) ?: throw UserNotFoundException(dto.userUid)
            if (userPo.associateAccount.isNullOrBlank()) throw AccountNotFoundException(dto.userUid)
            //查询账户信息
            val accountPo = bookAccountRepository.findByUid(userPo.associateAccount) ?: throw AccountNotFoundException(userPo.associateAccount!!)
            //查看班级、幼儿园信息
            val classInfo = levelRepository.queryByUid(accountPo.classUid!!, LevelTypeEnum.Classroom) ?: throw ClassNotExistException()
            //查询年级
            val gradeInfo = levelRepository.queryByUid(classInfo.parentUid!!, LevelTypeEnum.Grade) ?: throw ClassNotExistException()
            //查询园区
            val gardenInfo = levelRepository.queryByUid(gradeInfo.parentUid!!, LevelTypeEnum.Garden) ?: throw ClassNotExistException()
            //添加数据
            val readCircleUid = UUIDUtil.createUUID()
            val bookReadCirclePo = buildPublishBookReadCirclePo(readCircleUid, dto, accountPo, gardenInfo)
            bookReadCircleRepository.save(bookReadCirclePo)
            //添加附件
            val attachmentPos = buildPublishBookReadCircleAttachment(dto, readCircleUid)
            if (!attachmentPos.isNullOrEmpty()) bookReadCircleAttachmentRepository.saveBatch(attachmentPos)
        } finally {
            if (lock.isHeldByCurrentThread) {
                lock.unlock()
            }
        }
    }

}