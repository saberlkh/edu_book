package com.edu.book.domain.book.dto

import java.io.Serializable
import org.apache.commons.lang3.math.NumberUtils

/**
 * @Auther: liukaihua
 * @Date: 2024/5/21 21:43
 * @Description:
 */
class BookAgeGroupDto: Serializable {

    /**
     * 年龄段名称
     */
    var ageGroupName: String = ""

    /**
     * code
     */
    var ageGroupCode: Int = NumberUtils.INTEGER_ONE

    companion object {

        fun buildBookAgeGroupDto(ageGroupName: String, ageGroupCode: Int): BookAgeGroupDto {
            return BookAgeGroupDto().apply {
                this.ageGroupCode = ageGroupCode
                this.ageGroupName = ageGroupName
            }
        }

    }

}