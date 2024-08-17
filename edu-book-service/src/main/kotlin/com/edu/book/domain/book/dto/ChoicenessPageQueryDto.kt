package com.edu.book.domain.book.dto

import java.io.Serializable
import javax.validation.constraints.NotBlank
import org.apache.commons.lang3.math.NumberUtils

/**
 * @Auther: liukaihua
 * @Date: 2024/8/15 20:50
 * @Description:
 */
class ChoicenessPageQueryDto: Serializable {

    @NotBlank(message = "园区uid不能为空")
    var gardenUid: String = ""

    var page: Int = NumberUtils.INTEGER_ONE

    var pageSize: Int = 20

}