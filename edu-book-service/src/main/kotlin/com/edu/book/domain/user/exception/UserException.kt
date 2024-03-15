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
 * 账户不存在
 */
class AccountNotFoundException(accountUid: String): UserException("账户不存在,${accountUid}")