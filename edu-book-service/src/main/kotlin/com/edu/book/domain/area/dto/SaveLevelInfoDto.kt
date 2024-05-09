package com.edu.book.domain.area.dto

import java.io.Serializable

/**
 * @Auther: liukaihua
 * @Date: 2024/5/8 23:46
 * @Description:
 */
class SaveLevelInfoDto: Serializable {

    var levelType: Int? = null

    var levelName: String? = null

    var parentUid: String? = null

    var provinceCode: String? = null

    var cityCode: String? = null

    var districtCode: String? = null

}