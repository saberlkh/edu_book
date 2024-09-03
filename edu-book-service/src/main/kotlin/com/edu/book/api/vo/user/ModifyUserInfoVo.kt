package com.edu.book.api.vo.user

import java.io.Serializable
import javax.validation.constraints.NotBlank

/**
 * @Auther: liukaihua
 * @Date: 2024/7/5 22:28
 * @Description:
 */
class ModifyUserInfoVo: Serializable {

    /**
     * 头像
     */
    @NotBlank(message = "头像不能为空")
    var photoUrl: String = ""

    /**
     * 班级Uid
     */
    var classUid: String = ""

}