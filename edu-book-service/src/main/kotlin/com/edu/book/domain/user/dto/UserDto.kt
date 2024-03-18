package com.edu.book.domain.user.dto

import java.io.Serializable

/**
 * @Auther: liukaihua
 * @Date: 2024/3/18 15:22
 * @Description:
 */
class UserDto: Serializable {

    /**
     * 业务唯一id
     */
    var uid: String? = null

    /**
     * 姓名
     */
    var name: String? = null

    /**
     * 昵称
     */
    var nickName: String? = null

    /**
     * 微信Uid
     */
    var wechatUid: String? = null

    /**
     * 手机号
     */
    var phone: String? = null

    /**
     * 关联账号
     */
    var associateAccount: String? = null

}