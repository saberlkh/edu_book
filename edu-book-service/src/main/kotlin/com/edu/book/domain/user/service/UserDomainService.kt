package com.edu.book.domain.user.service

import com.edu.book.domain.dto.RegisterUserDto
import com.edu.book.domain.user.mapper.UserEntityMapper.buildRegisterUserDto
import com.edu.book.domain.user.repository.BookAccountRepository
import com.edu.book.domain.user.repository.BookAccountRoleRelationRepository
import com.edu.book.domain.user.repository.BookUserRepository
import com.edu.book.infrastructure.config.SystemConfig
import com.edu.book.infrastructure.constants.RedisKeyConstant.REGISTER_USER_LOCK_KEY
import com.edu.book.infrastructure.po.user.BookUserPo
import com.edu.book.infrastructure.repositoryImpl.cache.repo.UserCacheRepo
import com.edu.book.infrastructure.repositoryImpl.user.BookRolePermissionRelationRepositoryImpl
import com.edu.book.infrastructure.util.UUIDUtil
import java.util.concurrent.TimeUnit
import javax.annotation.Resource
import org.apache.commons.lang3.StringUtils
import org.redisson.api.RedissonClient
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

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
    private lateinit var bookRolePermissionRelationRepositoryImpl: BookRolePermissionRelationRepositoryImpl

    /**
     * 用户注册
     * 如果用户存在 返回
     * 不存在则注册新用户
     * 并获取最新token
     */
    fun registerUser(openId: String): RegisterUserDto {
        val registerLockKey = REGISTER_USER_LOCK_KEY + openId
        val lock = redissonClient.getLock(registerLockKey)
        try {
            if (!lock.tryLock(systemConfig.distributedLockWaitTime, systemConfig.distributedLockReleaseTime, TimeUnit.MILLISECONDS)) {
                throw ConcurrentModificationException(openId)
            }
            //根据openid查询用户
            val userPo = bookUserRepository.findUserByOpenId(openId)
            //如果用户为空，创建一个新用户
            val finalUserPo = if (userPo != null) {
                userPo
            } else {
                val newUserPo = BookUserPo().apply {
                    this.uid = UUIDUtil.createUUID()
                    this.name = StringUtils.EMPTY
                    this.nickName = StringUtils.EMPTY
                    this.wechatUid = openId
                    this.phone = ""
                    this.associateAccount = StringUtils.EMPTY
                }
                bookUserRepository.save(newUserPo)
                newUserPo
            }
            //获取账号绑定信息
            val accountPo = bookAccountRepository.findByUid(finalUserPo.associateAccount)
            //获取角色和权限信息
            val accountRoleRelationPo = bookAccountRoleRelationRepository.findByAccountUid(accountPo?.accountUid)
            val rolePermissionRelations = bookRolePermissionRelationRepositoryImpl.findListByRoleUid(accountRoleRelationPo?.roleUid)
            //获取并设置token
            val token = UUIDUtil.createUUID()
            userCacheRepo.setUserToken(finalUserPo.uid!!, token)
            //组装数据
            return buildRegisterUserDto(finalUserPo, rolePermissionRelations, accountRoleRelationPo, accountPo)
        } finally {
            if (lock.isHeldByCurrentThread) {
                lock.unlock()
            }
        }
    }

}