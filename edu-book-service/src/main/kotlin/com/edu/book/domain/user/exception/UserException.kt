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