package com.edu.book.domain.book.dto

import java.io.Serializable
import org.apache.commons.lang3.math.NumberUtils

/**
 * @Auther: liukaihua
 * @Date: 2024/5/23 22:24
 * @Description:
 */
class PageQueryBorrowBookResultDto: Serializable {

    /**
     * 图书自编码
     */
    var bookUid: String = ""

    /**
     * 图片地址
     */
    var picUrl: String? = null

    /**
     * 标题
     */
    var title: String? = null

    /**
     * 子标题
     */
    var subTitle: String? = null

    /**
     * 作者
     */
    var author: String? = null

    /**
     * 借阅时间
     */
    var borrowTime: Long? = null

    /**
     * 归还时间
     */
    var returnTime: Long? = null

    /**
     * 借阅状态
     */
    var borrowStatus: Int? = null

    /**
     * 剩余天数
     */
    var returnDay: Int = NumberUtils.INTEGER_ZERO

}