package com.edu.book.domain.read.dto

import java.io.Serializable
import org.apache.commons.lang3.math.NumberUtils

/**
 * @Auther: liukaihua
 * @Date: 2024/6/4 19:49
 * @Description:
 */
class PageReadCircleDto: Serializable {

    /**
     * uid
     */
    var readCircleUid: String = ""

    /**
     * 文本
     */
    var readText: String = ""

    /**
     * 用户
     */
    var userUid: String = ""

    /**
     * 用户名
     */
    var username: String = ""

    /**
     * 昵称
     */
    var nickname: String = ""

    /**
     * studentName
     */
    var studentName: String = ""

    /**
     * 班级uid
     */
    var classUid: String = ""

    /**
     * 班级名称
     */
    var className: String = ""

    /**
     * 园区Uid
     */
    var gardenUid: String = ""

    /**
     * 园区名称
     */
    var gardenName: String = ""

    /**
     * 年级
     */
    var gradeUid: String = ""

    /**
     * 年级
     */
    var gradeName: String = ""

    /**
     * 幼儿园
     */
    var kindergartenUid: String = ""

    /**
     * 幼儿园
     */
    var kindergartenName: String = ""

    /**
     * 附件
     */
    var attachments: List<ReadCircleAttachmentDto> = emptyList()

    /**
     * 点赞
     */
    var likes: List<ReadCircleLikeDto> = emptyList()

    /**
     * 评论
     */
    var comments: List<ReadCircleCommentDto> = emptyList()

    /**
     * 创建时间
     */
    var createTime: Long = NumberUtils.LONG_ZERO

    /**
     * 头像
     */
    var photoUrl: String = ""

    /**
     * 阅读天数
     */
    var readDay: Int = NumberUtils.INTEGER_ZERO

    /**
     * 阅读书籍数量
     */
    var readBookCount: Int = NumberUtils.INTEGER_ZERO

}