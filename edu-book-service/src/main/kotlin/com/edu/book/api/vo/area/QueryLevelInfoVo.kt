package com.edu.book.api.vo.area

import java.io.Serializable

/**
 * @Auther: liukaihua
 * @Date: 2024/5/9 11:36
 * @Description:
 */
class QueryLevelInfoVo: Serializable {

    var levelType: Int? = null

    var provinceCode: String? = null

    var cityCode: String? = null

    var districtCode: String? = null

    var parentUid: String? = null

}