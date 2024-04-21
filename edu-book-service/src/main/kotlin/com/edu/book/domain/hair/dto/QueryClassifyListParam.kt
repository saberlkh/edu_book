package com.edu.book.domain.hair.dto

import com.edu.book.infrastructure.enums.SortTypeEnum
import java.io.Serializable

/**
 * @Auther: liukaihua
 * @Date: 2024/4/21 11:25
 * @Description:
 */
class QueryClassifyListParam: Serializable {

    var sortType: String? = SortTypeEnum.ASC.sortType

}