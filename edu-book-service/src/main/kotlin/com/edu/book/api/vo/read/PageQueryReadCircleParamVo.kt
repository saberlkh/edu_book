package com.edu.book.api.vo.read

import java.io.Serializable
import javax.validation.constraints.NotBlank
import org.apache.commons.lang3.math.NumberUtils

/**
 * @Auther: liukaihua
 * @Date: 2024/6/4 23:36
 * @Description:
 */
class PageQueryReadCircleParamVo: Serializable {

    /**
     * 园区Uid
     */
    @NotBlank(message = "园区id不能为空")
    var gardenUid: String = ""

    /**
     * page
     */
    var page: Int = NumberUtils.INTEGER_ONE

    /**
     * pageSize
     */
    var pageSize: Int = 10

}