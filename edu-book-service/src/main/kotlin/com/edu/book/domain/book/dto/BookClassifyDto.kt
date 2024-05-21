package com.edu.book.domain.book.dto

import java.io.Serializable

/**
 * @Auther: liukaihua
 * @Date: 2024/5/18 13:57
 * @Description:
 */
class BookClassifyDto: Serializable {

    /**
     * 分类名称
     */
    var classifyName: String = ""

    /**
     * uid
     */
    var classifyCode: String = ""

    companion object {

        fun buildBookClassifyDto(classifyName: String, classifyCode: String): BookClassifyDto {
            return BookClassifyDto().apply {
                this.classifyName = classifyName
                this.classifyCode = classifyCode
            }
        }

    }

}