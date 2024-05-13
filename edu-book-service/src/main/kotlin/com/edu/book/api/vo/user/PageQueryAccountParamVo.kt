package com.edu.book.api.vo.user

import java.io.Serializable
import javax.validation.constraints.NotBlank
import org.apache.commons.lang3.math.NumberUtils

/**
 * @Auther: liukaihua
 * @Date: 2024/5/13 22:25
 * @Description:
 */
class PageQueryAccountParamVo: Serializable {

    /**
     * page
     */
    var page: Int = NumberUtils.INTEGER_ONE

    /**
     * pageSize
     */
    var pageSize: Int = 10

    /**
     * 班级uid
     */
    @NotBlank(message = "班级uid不能为空")
    var classUid: String = ""

}