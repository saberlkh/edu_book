package com.edu.book.domain.read.dto

import com.edu.book.domain.read.enums.ReadCircleLikeStatusEnum
import java.io.Serializable

/**
 * @Auther: liukaihua
 * @Date: 2024/6/5 16:27
 * @Description:
 */
class LikeReadCircleDto: Serializable {

    /**
     * 用户uid
     */
    var userUid: String = ""

    /**
     * 阅读圈id
     */
    var readCircleUid: String = ""

    /**
     * 点赞状态
     */
    var likeStatus: Int = ReadCircleLikeStatusEnum.LIKE.status

}