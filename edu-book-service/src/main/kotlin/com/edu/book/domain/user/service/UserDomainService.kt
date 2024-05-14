package com.edu.book.domain.user.service

import cn.afterturn.easypoi.excel.entity.ExportParams
import com.edu.book.domain.area.enums.LevelTypeEnum
import com.edu.book.domain.area.repository.AreaRepository
import com.edu.book.domain.area.repository.LevelRepository
import com.edu.book.domain.user.dto.BindAccountDto
import com.edu.book.domain.user.dto.BindAccountRespDto
import com.edu.book.domain.user.dto.ExportExcelAccountDto
import com.edu.book.domain.user.dto.PageQueryAccountDto
import com.edu.book.domain.user.dto.PageQueryAccountParamDto
import com.edu.book.domain.user.dto.RegisterUserDto
import com.edu.book.domain.user.dto.UnbindAccountDto
import com.edu.book.domain.user.dto.UnbindAccountRespDto
import com.edu.book.domain.user.dto.UploadFileCreateAccountDto
import com.edu.book.domain.user.dto.UserDto
import com.edu.book.domain.user.enums.BookRoleEnum
import com.edu.book.domain.user.exception.AccountBindedException
import com.edu.book.domain.user.exception.AccountNotFoundException
import com.edu.book.domain.user.exception.AreaInfoNotExistException
import com.edu.book.domain.user.exception.ClassNotExistException
import com.edu.book.domain.user.exception.ConcurrentCreateInteractRoomException
import com.edu.book.domain.user.exception.UserBindedException
import com.edu.book.domain.user.exception.UserNotFoundException
import com.edu.book.domain.user.exception.UserTokenExpiredException
import com.edu.book.domain.user.exception.UserUnBindedException
import com.edu.book.domain.user.mapper.UserEntityMapper.bindBindAccountRespDto
import com.edu.book.domain.user.mapper.UserEntityMapper.buildBindAccountUserRelationPo
import com.edu.book.domain.user.mapper.UserEntityMapper.buildBookAccountRoleRelationPo
import com.edu.book.domain.user.mapper.UserEntityMapper.buildExportExcelAccountDto
import com.edu.book.domain.user.mapper.UserEntityMapper.buildPageQueryAccountDto
import com.edu.book.domain.user.mapper.UserEntityMapper.buildRegisterUserDto
import com.edu.book.domain.user.mapper.UserEntityMapper.buildUpdateUserPo
import com.edu.book.domain.user.mapper.UserEntityMapper.buildUploadBookAccountPo
import com.edu.book.domain.user.mapper.UserEntityMapper.registerUserBuildUserPo
import com.edu.book.domain.user.repository.BookAccountRepository
import com.edu.book.domain.user.repository.BookAccountRoleRelationRepository
import com.edu.book.domain.user.repository.BookAccountUserRelationRepository
import com.edu.book.domain.user.repository.BookRoleBasicRepository
import com.edu.book.domain.user.repository.BookRolePermissionRelationRepository
import com.edu.book.domain.user.repository.BookUserRepository
import com.edu.book.infrastructure.config.SystemConfig
import com.edu.book.infrastructure.constants.Constants.account_download_file_attname
import com.edu.book.infrastructure.constants.Constants.account_download_file_name
import com.edu.book.infrastructure.constants.Constants.account_upload_file_name
import com.edu.book.infrastructure.constants.Constants.eight
import com.edu.book.infrastructure.constants.RedisKeyConstant.BIND_UNBIND_USER_ACCOUNT_LOCK_KEY
import com.edu.book.infrastructure.constants.RedisKeyConstant.REGISTER_USER_LOCK_KEY
import com.edu.book.infrastructure.enums.FileTypeEnum
import com.edu.book.infrastructure.po.user.BookAccountPo
import com.edu.book.infrastructure.po.user.BookAccountRoleRelationPo
import com.edu.book.infrastructure.po.user.BookUserPo
import com.edu.book.infrastructure.repositoryImpl.cache.repo.UserCacheRepo
import com.edu.book.infrastructure.util.ExcelUtils
import com.edu.book.infrastructure.util.MapperUtil
import com.edu.book.infrastructure.util.QiNiuUtil
import com.edu.book.infrastructure.util.RandomUtil
import com.edu.book.infrastructure.util.UUIDUtil
import com.edu.book.infrastructure.util.page.Page
import java.sql.Timestamp
import java.util.Date
import java.util.concurrent.TimeUnit
import javax.annotation.Resource
import javax.management.relation.RoleNotFoundException
import org.apache.commons.lang3.StringUtils
import org.apache.commons.lang3.math.NumberUtils
import org.redisson.api.RedissonClient
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @Auther: liukaihua
 * @Date: 2024/3/13 22:31
 * @Description:
 */

@Service
class UserDomainService {

    private val logger = LoggerFactory.getLogger(UserDomainService::class.java)

    @Resource
    private lateinit var redissonClient: RedissonClient

    @Autowired
    private lateinit var bookUserRepository: BookUserRepository

    @Autowired
    private lateinit var bookAccountRepository: BookAccountRepository

    @Autowired
    private lateinit var bookAccountRoleRelationRepository: BookAccountRoleRelationRepository

    @Autowired
    private lateinit var systemConfig: SystemConfig

    @Autowired
    private lateinit var userCacheRepo: UserCacheRepo

    @Autowired
    private lateinit var bookRolePermissionRelationRepository: BookRolePermissionRelationRepository

    @Autowired
    private lateinit var bookAccountUserRelationRepository: BookAccountUserRelationRepository

    @Autowired
    private lateinit var qiNiuUtil: QiNiuUtil

    @Autowired
    private lateinit var levelRepository: LevelRepository

    @Autowired
    private lateinit var bookRoleBasicRepository: BookRoleBasicRepository

    @Autowired
    private lateinit var areaRepository: AreaRepository

    /**
     * 分页查询
     */
    fun pageQueryAccountListByClass(param: PageQueryAccountParamDto): Page<PageQueryAccountDto> {
        val pageQuery = bookAccountRepository.pageQuery(param)
        if (pageQuery.records.isNullOrEmpty()) return Page()
        //查看班级、幼儿园信息
        val classInfo = levelRepository.queryByUid(param.classUid, LevelTypeEnum.Classroom) ?: throw ClassNotExistException()
        //查询年级
        val gradeInfo = levelRepository.queryByUid(classInfo.parentUid!!, LevelTypeEnum.Grade) ?: throw ClassNotExistException()
        //查询园区
        val gardenInfo = levelRepository.queryByUid(gradeInfo.parentUid!!, LevelTypeEnum.Garden) ?: throw ClassNotExistException()
        //查询幼儿园
        val kindergartenInfo = levelRepository.queryByUid(gardenInfo.parentUid!!, LevelTypeEnum.Kindergarten) ?: throw ClassNotExistException()
        //查询省市区
        val areaInfos = areaRepository.batchQueryByAreaCode(listOf(kindergartenInfo.provinceId!!, kindergartenInfo.cityId!!, kindergartenInfo.districtId!!))
            ?: throw AreaInfoNotExistException()
        //参数组装
        val finalResult = pageQuery.records.map { po ->
            buildPageQueryAccountDto(areaInfos, po, kindergartenInfo, gardenInfo, classInfo)
        }
        return Page(param.page, param.pageSize, pageQuery.total.toInt(), finalResult)
    }

    /**
     * 生成借阅卡Id
     */
    fun generatorBorrwoCardId(): String {
        var newCardId = RandomUtil.getRandomNum(eight)
        var forEachCount = NumberUtils.INTEGER_ZERO
        //判断当前卡是否存在
        var currentAccountPo = bookAccountRepository.findByBorrwoCardId(newCardId) ?: return newCardId
        while (currentAccountPo != null) {
            if (forEachCount >= NumberUtils.INTEGER_TWO) break
            //在生成一个新的账号
            newCardId = RandomUtil.getRandomNum(eight)
            currentAccountPo = bookAccountRepository.findByBorrwoCardId(newCardId) ?: break
            forEachCount += NumberUtils.INTEGER_ONE
        }
        return newCardId
    }

    /**
     * 生成账号
     * 1.查看班级、幼儿园等信息
     * 2.创建账号并插入数据
     * 3.导入到文件
     * 4.上传七牛
     * 5.获取下载地址
     */
    @Transactional
    fun uploadFileCreateAccount(accountDto: UploadFileCreateAccountDto): String {
        //查看班级、幼儿园信息
        val classInfo = levelRepository.queryByUid(accountDto.classUid, LevelTypeEnum.Classroom) ?: throw ClassNotExistException()
        //查询年级
        val gradeInfo = levelRepository.queryByUid(classInfo.parentUid!!, LevelTypeEnum.Grade) ?: throw ClassNotExistException()
        //查询园区
        val gardenInfo = levelRepository.queryByUid(gradeInfo.parentUid!!, LevelTypeEnum.Garden) ?: throw ClassNotExistException()
        //查询幼儿园
        val kindergartenInfo = levelRepository.queryByUid(gardenInfo.parentUid!!, LevelTypeEnum.Kindergarten) ?: throw ClassNotExistException()
        //查询省市区
        val areaInfos = areaRepository.batchQueryByAreaCode(listOf(kindergartenInfo.provinceId!!, kindergartenInfo.cityId!!, kindergartenInfo.districtId!!))
            ?: throw AreaInfoNotExistException()
        //创建账号 初始化角色 游客
        val accountRoles = mutableListOf<BookAccountRoleRelationPo>()
        //查询角色 游客
        val visitorRoleInfo = bookRoleBasicRepository.queryByRoleCode(BookRoleEnum.visitor.name) ?: throw RoleNotFoundException()
        //查询当前存在的账号信息
        val existAccountPos = bookAccountRepository.findByParentPhone(accountDto.accountList.mapNotNull { it.parentPhone }) ?: emptyList()
        val existToUploadAccountPos = mutableListOf<BookAccountPo>()
        val saveAccounts = accountDto.accountList.mapNotNull { accountDto ->
            val existAccountPo = existAccountPos.filter { StringUtils.equals(it.parentPhone, accountDto.parentPhone) && StringUtils.equals(it.studentName, accountDto.studentName) }.firstOrNull()
            if (existAccountPo == null) {
                val uid = UUIDUtil.createUUID()
                //生成借阅卡Id
                val borrowCardId = generatorBorrwoCardId()
                val accountPo = buildUploadBookAccountPo(uid, kindergartenInfo, classInfo, accountDto, borrowCardId)
                val accountRole = buildBookAccountRoleRelationPo(uid, visitorRoleInfo)
                accountRoles.add(accountRole)
                accountPo
            } else {
                existToUploadAccountPos.add(existAccountPo)
                null
            }
        }
        //添加数据
        bookAccountRepository.saveBatch(saveAccounts)
        bookAccountRoleRelationRepository.saveBatch(accountRoles)
        //导出数据到excel文件
        val exportData = (saveAccounts + existToUploadAccountPos).map {
            buildExportExcelAccountDto(it, kindergartenInfo, gardenInfo, classInfo, areaInfos)
        }
        val file = ExcelUtils.exportToFile(exportData, ExportExcelAccountDto::class.java, account_upload_file_name, null, ExportParams())
        val respDto = qiNiuUtil.upload(file.inputStream(), FileTypeEnum.VIDEO.fileType)
        file.delete()
        return respDto.filePath + account_download_file_attname + kindergartenInfo.levelName + classInfo.levelName + account_download_file_name
    }

    /**
     * 用户鉴权
     * 通过token返回用户信息
     */
    fun authUser(token: String): UserDto {
        //获取缓存
        val tokenCacheDto = userCacheRepo.getUserToken(token) ?: throw UserTokenExpiredException()
        //获取用户信息
        val userPo = bookUserRepository.findByUserUid(tokenCacheDto.userUid) ?: throw UserNotFoundException(tokenCacheDto.userUid)
        return MapperUtil.map(UserDto::class.java, userPo)
    }

    /**
     * 解绑账号
     */
    @Transactional(rollbackFor = [Exception::class])
    fun userUnbindAccount(dto: UnbindAccountDto): UnbindAccountRespDto {
        val unbindLockKey = BIND_UNBIND_USER_ACCOUNT_LOCK_KEY + dto.openId
        val lock = redissonClient.getLock(unbindLockKey)
        try {
            if (!lock.tryLock(systemConfig.distributedLockWaitTime, systemConfig.distributedLockReleaseTime, TimeUnit.MILLISECONDS)) {
                throw ConcurrentCreateInteractRoomException(dto.openId)
            }
            //查询用户信息
            val userPo = bookUserRepository.findUserByOpenId(dto.openId) ?: throw UserNotFoundException(dto.openId)
            //如果用户已经解绑，进行提示
            if (StringUtils.isBlank(userPo.associateAccount)) throw UserUnBindedException()
            //修改用户信息
            val updateUserPo = MapperUtil.map(BookUserPo::class.java, userPo).apply {
                this.associateAccount = ""
                this.updateTime = Timestamp(Date().time)
            }
            bookUserRepository.updateUserPoByUid(updateUserPo)
            //删除账号用户绑定记录关联数据
            bookAccountUserRelationRepository.removeByAccountUid(userPo.associateAccount)
            return UnbindAccountRespDto().apply {
                this.phone = userPo.phone ?: ""
                this.userUid = userPo.uid ?: ""
                this.openId = userPo.wechatUid ?: ""
                this.username = userPo.name ?: ""
                this.nickname = userPo.nickName ?: ""
            }
        } finally {
            if (lock.isHeldByCurrentThread) {
                lock.unlock()
            }
        }
    }

    /**
     * 绑定账号
     * 账号已经存在，需要进行绑定
     */
    @Transactional(rollbackFor = [Exception::class])
    fun userBindAccount(dto: BindAccountDto): BindAccountRespDto {
        val bindLockKey = BIND_UNBIND_USER_ACCOUNT_LOCK_KEY + dto.openId
        val lock = redissonClient.getLock(bindLockKey)
        try {
            if (!lock.tryLock(systemConfig.distributedLockWaitTime, systemConfig.distributedLockReleaseTime, TimeUnit.MILLISECONDS)) {
                throw ConcurrentCreateInteractRoomException(dto.openId)
            }
            //查询用户信息
            val userPo = bookUserRepository.findUserByOpenId(dto.openId) ?: throw UserNotFoundException(dto.openId)
            //如果用户已经绑定账号，进行提示
            if (StringUtils.isNotBlank(userPo.associateAccount)) throw UserBindedException(userPo.uid!!)
            //判断账号是否被绑定
            val currentUserAccountRelationPo = bookAccountUserRelationRepository.findByAccountUid(dto.accountUid)
            if (currentUserAccountRelationPo != null) throw AccountBindedException()
            //查询账号信息
            val accountPo = bookAccountRepository.findByUid(dto.accountUid) ?: throw AccountNotFoundException(dto.accountUid)
            //修改用户数据，并插入用户账号关联数据
            val updateUserPo = buildUpdateUserPo(userPo, accountPo, dto)
            bookUserRepository.updateUserPoByUid(updateUserPo)
            val userAccountRelationPo = buildBindAccountUserRelationPo(userPo.uid!!, accountPo.accountUid!!, UUIDUtil.createUUID())
            bookAccountUserRelationRepository.save(userAccountRelationPo)
            //获取角色和权限信息
            val accountRoleRelationPo = bookAccountRoleRelationRepository.findByAccountUid(accountPo.accountUid)
            val rolePermissionRelations = bookRolePermissionRelationRepository.findListByRoleUid(accountRoleRelationPo?.roleUid)
            val finalUserPo = bookUserRepository.findUserByOpenId(dto.openId) ?: throw UserNotFoundException(dto.openId)
            return bindBindAccountRespDto(accountPo, finalUserPo, rolePermissionRelations, accountRoleRelationPo)
        } finally {
            if (lock.isHeldByCurrentThread) {
                lock.unlock()
            }
        }
    }

    /**
     * 用户注册
     * 如果用户存在 返回
     * 不存在则注册新用户
     * 并获取最新token
     */
    @Transactional(rollbackFor = [Exception::class])
    fun registerUser(openId: String): RegisterUserDto {
        val registerLockKey = REGISTER_USER_LOCK_KEY + openId
        val lock = redissonClient.getLock(registerLockKey)
        try {
            if (!lock.tryLock(systemConfig.distributedLockWaitTime, systemConfig.distributedLockReleaseTime, TimeUnit.MILLISECONDS)) {
                throw ConcurrentCreateInteractRoomException(openId)
            }
            //根据openid查询用户
            val userPo = bookUserRepository.findUserByOpenId(openId)
            //如果用户为空，创建一个新用户
            val finalUserPo = if (userPo != null) {
                userPo
            } else {
                val newUserPo = registerUserBuildUserPo(openId)
                bookUserRepository.save(newUserPo)
                newUserPo
            }
            //获取账号绑定信息
            val accountPo = bookAccountRepository.findByUid(finalUserPo.associateAccount)
            //获取角色和权限信息
            val accountRoleRelationPo = bookAccountRoleRelationRepository.findByAccountUid(accountPo?.accountUid)
            val rolePermissionRelations = bookRolePermissionRelationRepository.findListByRoleUid(accountRoleRelationPo?.roleUid)
            //获取并设置token
            val token = UUIDUtil.createUUID()
            userCacheRepo.setUserToken(finalUserPo.uid!!, token)
            //组装数据
            return buildRegisterUserDto(finalUserPo, rolePermissionRelations, accountRoleRelationPo, accountPo, token)
        } finally {
            if (lock.isHeldByCurrentThread) {
                lock.unlock()
            }
        }
    }

}