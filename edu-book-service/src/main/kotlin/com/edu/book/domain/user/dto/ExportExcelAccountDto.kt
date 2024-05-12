package com.edu.book.domain.user.dto

import cn.afterturn.easypoi.excel.annotation.Excel
import java.io.Serializable

/**
 * @Auther: liukaihua
 * @Date: 2024/5/12 21:20
 * @Description:
 */
class ExportExcelAccountDto: Serializable {

    /**
     * 借阅卡id
     */
    @Excel(name = "借阅卡id", needMerge = true)
    var borrowCardId: String? = null

    /**
     * 学生名称
     */
    @Excel(name = "学生名称", needMerge = true)
    var studentName: String? = null

    /**
     * 账号Uid
     */
    @Excel(name = "账号Uid", needMerge = true)
    var accountUid: String? = null

    /**
     * 密码
     */
    @Excel(name = "密码", needMerge = true)
    var password: String? = null

    /**
     * 押金
     */
    @Excel(name = "押金", needMerge = true)
    var cashPledge: Int? = null

    /**
     * 账号过期时间
     */
    @Excel(name = "账号过期时间", needMerge = true)
    var expireTime: String? = null

    /**
     * 家长手机号
     */
    @Excel(name = "家长手机号", needMerge = true)
    var parentPhone: String? = null

    /**
     * 省Id
     */
    @Excel(name = "省", needMerge = true)
    var provinceName: String? = null

    /**
     * 市Id
     */
    @Excel(name = "市", needMerge = true)
    var cityName: String? = null

    /**
     * 区Id
     */
    @Excel(name = "区", needMerge = true)
    var districtName: String? = null

    /**
     * 幼儿园
     */
    @Excel(name = "幼儿园", needMerge = true)
    var kindergartenName: String? = null

    /**
     * 园区
     */
    @Excel(name = "园区", needMerge = true)
    var gradenName: String? = null

    /**
     * 班级名称
     */
    @Excel(name = "班级名称", needMerge = true)
    var className: String? = null

}