package com.edu.book.domain.area.dto

import java.io.Serializable

/**
 * @Auther: liukaihua
 * @Date: 2024/5/9 11:32
 * @Description:
 */
class QueryLevelInfoDto: Serializable {

    var levelType: Int? = null

    var provinceCode: String? = null

    var cityCode: String? = null

    var districtCode: String? = null

}