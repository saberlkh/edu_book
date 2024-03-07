package com.edu.book.infrastructure.util

/**
 * @Auther: liukaihua
 * @Date: 2024/3/7 10:51
 * @Description:
 */
class DataMap : LinkedHashMap<String, Any?> {

    constructor()

    fun addAttribute(attributeName: String, attributeValue: Any?): DataMap {
        this[attributeName] = attributeValue
        return this
    }
}