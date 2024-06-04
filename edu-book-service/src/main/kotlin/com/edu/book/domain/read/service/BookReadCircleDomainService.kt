package com.edu.book.domain.read.service

import com.edu.book.domain.area.enums.LevelTypeEnum
import com.edu.book.domain.area.repository.LevelRepository
import com.edu.book.domain.read.dto.PageQueryReadCircleParam
import com.edu.book.domain.read.dto.PageReadCircleDto
import com.edu.book.domain.read.dto.PublishReadCircleDto
import com.edu.book.domain.read.dto.ReadCircleAttachmentDto
import com.edu.book.domain.read.dto.ReadCircleCommentDto
import com.edu.book.domain.read.dto.ReadCircleLikeDto
import com.edu.book.domain.read.mapper.ReadCircleEntityMapper.buildPageQueryCircleDto
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
import com.edu.book.infrastructure.util.page.Page
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
     * 分页查询阅读圈
     */
    fun pageQueryReadCircle(param: PageQueryReadCircleParam): Page<PageReadCircleDto> {
        val pageQuery = bookReadCircleRepository.pageQueryReadCircle(param)
        if (pageQuery.records.isNullOrEmpty()) return Page()
        val readCircleUids = pageQuery.records.mapNotNull { it.uid }
        //查询附件信息
        val readCircleAttachments = bookReadCircleAttachmentRepository.batchQueryByCircleUids(readCircleUids) ?: emptyList()
        val fileKeys = readCircleAttachments.mapNotNull { it.fileKey!! }.distinct()
        val uploadInfos = uploadFileRepository.batchQuery(fileKeys) ?: emptyList()
        val uploadInfoMap = uploadInfos.associateBy { it.fileKey!! }
        val readCircleAttachmentMap = readCircleAttachments.groupBy { it.readCircleUid!! }
        //查询点赞信息
        val likes = bookReadCircleLikeFlowRepository.batchQueryByCircleUids(readCircleUids) ?: emptyList()
        val likeMap = likes.groupBy { it.readCircleUid!! }
        //查询评论信息
        val comments = bookReadCircleCommentFlowRepository.batchQueryByCircleUids(readCircleUids) ?: emptyList()
        val commentMap = comments.groupBy { it.readCircleUid!! }
        //查询用户信息
        val allUserUids = (pageQuery.records.mapNotNull { it.userUid }
                + likes.mapNotNull { it.userUid } + comments.mapNotNull { it.commentUserUid } + comments.mapNotNull { it.commentedUserUid }).distinct()
        val userPos = bookUserRepository.batchQueryByUserUids(allUserUids) ?: emptyList()
        val userPoMap = userPos.associateBy { it.uid!! }
        //查询账户信息
        val allAccountUids = userPos.mapNotNull { it.associateAccount!! }.distinct()
        val accountPos = bookAccountRepository.batchQueryByAccountUids(allAccountUids) ?: emptyList()
        val accountPoMap = accountPos.associateBy { it.accountUid!! }
        //查询班级信息
        val allClassUids = accountPos.mapNotNull { it.classUid }.distinct()
        val classList = levelRepository.batchQueryByUids(allClassUids, LevelTypeEnum.Classroom) ?: emptyList()
        val classMap = classList.associateBy { it.uid!! }
        //查询年级
        val allGradeUids = classList.mapNotNull { it.parentUid }.distinct()
        val gradeList = levelRepository.batchQueryByUids(allGradeUids, LevelTypeEnum.Grade) ?: emptyList()
        val gradeMap = gradeList.associateBy { it.uid!! }
        //查询园区
        val allGardenUids = gradeList.mapNotNull { it.parentUid }.distinct()
        val gardenList = levelRepository.batchQueryByUids(allGardenUids, LevelTypeEnum.Garden) ?: emptyList()
        val gardenMap = gardenList.associateBy { it.uid!! }
        //查询幼儿园
        val allKindergartenUids = gardenList.mapNotNull { it.parentUid }.distinct()
        val kindergartenList = levelRepository.batchQueryByUids(allKindergartenUids, LevelTypeEnum.Kindergarten) ?: emptyList()
        val kindergartenMap = kindergartenList.associateBy { it.uid!! }
        //数据组装
        val result = buildPageQueryCircleDto(pageQuery, userPoMap, accountPoMap, classMap, gradeMap, gardenMap, kindergartenMap, readCircleAttachmentMap, likeMap, commentMap, uploadInfoMap)
        return Page(param.page, param.pageSize, pageQuery.total.toInt(), result)
    }

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