package com.edu.book.api.vo.read

import com.edu.book.domain.read.enums.ReadCircleLikeStatusEnum
import java.io.Serializable
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

/**
 * @Auther: liukaihua
 * @Date: 2024/6/5 19:59
 * @Description:
 */
class LikeReadCircleVo: Serializable {

    /**
     * 用户uid
     */
    @NotBlank(message = "用户Uid不能为空")
    var userUid: String = ""

    /**
     * 阅读圈id
     */
    @NotBlank(message = "阅读圈不能为空")
    var circleUid: String = ""

    /**
     * 点赞状态
     */
    @NotNull(message = "点赞状态不能为空")
    var likeStatus: Int = ReadCircleLikeStatusEnum.LIKE.status

}