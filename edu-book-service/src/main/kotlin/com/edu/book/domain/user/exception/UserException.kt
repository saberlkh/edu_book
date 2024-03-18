package com.edu.book.domain.user.exception

import java.io.Serializable

/**
 * @Auther: liukaihua
 * @Date: 2024/3/13 22:40
 * @Description:
 */
open class UserException: RuntimeException, Serializable {

    constructor()

    constructor(message: String): super(message)

}

/**
 * 并发竞争锁失败
 */
class ConcurrentCreateInteractRoomException(openId: String): UserException("并发竞争锁失败,${openId}")

/**
 * 没有找到该用户
 */
class UserNotFoundException(openId: String): UserException("没有找到该用户,${openId}")

/**
 * 用户已经绑定
 */
class UserBindedException(userUid: String): UserException("用户已经绑定账户,${userUid}")

/**
 * 用户已经解绑
 */
class UserUnBindedException: UserException("用户已经解绑")

/**
 * 账户不存在
 */
class AccountNotFoundException(accountUid: String): UserException("账户不存在,${accountUid}")

/**
 * 账号已经被绑定
 */
class AccountBindedException(): UserException("账号已经被绑定")

/**
 * token失效
 */
class UserTokenExpiredException: UserException("token失效")