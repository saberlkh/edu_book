package com.edu.book.api.vo.user

import java.io.Serializable

/**
 * @Auther: liukaihua
 * @Date: 2024/5/13 22:25
 * @Description:
 */
class PageQueryAccountVo: Serializable {

    /**
     * 借阅卡id
     */
    var borrowCardId: String? = null

    /**
     * 学生名称
     */
    var studentName: String? = null

    /**
     * 账号Uid
     */
    var accountUid: String? = null

    /**
     * 密码
     */
    var password: String? = null

    /**
     * 押金
     */
    var cashPledge: Int? = null

    /**
     * 账号过期时间
     */
    var expireTime: String? = null

    /**
     * 家长手机号
     */
    var parentPhone: String? = null

    /**
     * 省Id
     */
    var provinceName: String? = null

    /**
     * 市Id
     */
    var cityName: String? = null

    /**
     * 区Id
     */
    var districtName: String? = null

    /**
     * 幼儿园
     */
    var kindergartenName: String? = null

    /**
     * 园区
     */
    var gardenName: String? = null

    /**
     * 班级名称
     */
    var className: String? = null

    /**
     * 班级Uid
     */
    var classUid: String? = null

}