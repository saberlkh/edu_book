package com.edu.book.api.vo.area

import java.io.Serializable
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

/**
 * @Auther: liukaihua
 * @Date: 2024/5/9 11:15
 * @Description:
 */
class SaveLevelInfoVo: Serializable {

    @NotNull
    var levelType: Int? = null

    @NotBlank
    var levelName: String? = null

    var parentUid: String? = null

    @NotNull
    var provinceCode: String? = null

    @NotNull
    var cityCode: String? = null

    @NotNull
    var districtCode: String? = null

}