package com.edu.book.domain.user.service

import com.edu.book.domain.user.dto.BindAccountDto
import com.edu.book.domain.user.dto.BindAccountRespDto
import com.edu.book.domain.user.dto.RegisterUserDto
import com.edu.book.domain.user.exception.AccountBindedException
import com.edu.book.domain.user.exception.AccountNotFoundException
import com.edu.book.domain.user.exception.ConcurrentCreateInteractRoomException
import com.edu.book.domain.user.exception.UserBindedException
import com.edu.book.domain.user.exception.UserNotFoundException
import com.edu.book.domain.user.mapper.UserEntityMapper.bindBindAccountRespDto
import com.edu.book.domain.user.mapper.UserEntityMapper.buildBindAccountUserRelationPo
import com.edu.book.domain.user.mapper.UserEntityMapper.buildRegisterUserDto
import com.edu.book.domain.user.mapper.UserEntityMapper.buildUpdateUserPo
import com.edu.book.domain.user.mapper.UserEntityMapper.registerUserBuildUserPo
import com.edu.book.domain.user.repository.BookAccountRepository
import com.edu.book.domain.user.repository.BookAccountRoleRelationRepository
import com.edu.book.domain.user.repository.BookAccountUserRelationRepository
import com.edu.book.domain.user.repository.BookRolePermissionRelationRepository
import com.edu.book.domain.user.repository.BookUserRepository
import com.edu.book.infrastructure.config.SystemConfig
import com.edu.book.infrastructure.constants.RedisKeyConstant.REGISTER_USER_LOCK_KEY
import com.edu.book.infrastructure.repositoryImpl.cache.repo.UserCacheRepo
import com.edu.book.infrastructure.util.UUIDUtil
import java.util.concurrent.TimeUnit
import javax.annotation.Resource
import org.apache.commons.lang3.StringUtils
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

    /**
     * 绑定账号
     * 账号已经存在，需要进行绑定
     */
    @Transactional(rollbackFor = [Exception::class])
    fun userBindAccount(dto: BindAccountDto): BindAccountRespDto {
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
        val updateUserPo = buildUpdateUserPo(userPo, accountPo, dto.phone)
        bookUserRepository.updateUserPoByUid(updateUserPo)
        val userAccountRelationPo = buildBindAccountUserRelationPo(userPo.uid!!, accountPo.accountUid!!, UUIDUtil.createUUID())
        bookAccountUserRelationRepository.save(userAccountRelationPo)
        //获取角色和权限信息
        val accountRoleRelationPo = bookAccountRoleRelationRepository.findByAccountUid(accountPo.accountUid)
        val rolePermissionRelations = bookRolePermissionRelationRepository.findListByRoleUid(accountRoleRelationPo?.roleUid)
        val finalUserPo = bookUserRepository.findUserByOpenId(dto.openId) ?: throw UserNotFoundException(dto.openId)
        return bindBindAccountRespDto(accountPo, finalUserPo, rolePermissionRelations, accountRoleRelationPo)
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