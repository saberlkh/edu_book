package com.edu.book.api.vo.book

import java.io.Serializable
import org.apache.commons.lang3.math.NumberUtils

/**
 * @Auther: liukaihua
 * @Date: 2024/5/23 23:06
 * @Description:
 */
class PageQueryBorrowBookResultVo: Serializable {

    /**
     * 图书自编码
     */
    var bookUid: String = ""

    /**
     * 图片地址
     */
    var pic: String? = null

    /**
     * 标题
     */
    var title: String? = null

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

    /**
     * subtitle
     */
    var subtitle: String? = null

    /**
     * 借阅人
     */
    var borrowUser: BorrowUserVo? = null

}

class BorrowUserVo: Serializable {

    var userUid: String = ""

    var nickname: String = ""

    var gardenUid: String = ""

    var gardenName: String = ""

}