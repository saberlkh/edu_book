package com.edu.book.domain.user.dto

import java.io.Serializable

/**
 * @Auther: liukaihua
 * @Date: 2024/7/5 22:26
 * @Description:
 */
class ModifyUserInfoDto: Serializable {

    /**
     * 用户Uid
     */
    var userUid: String = ""

    /**
     * 头像
     */
    var photoUrl: String = ""

    /**
     * 班级Uid
     */
    var classUid: String = ""

}