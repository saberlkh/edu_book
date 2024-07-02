package com.edu.book.domain.book.enums

/**
 * @Auther: liukaihua
 * @Date: 2024/7/2 21:23
 * @Description:
 */
enum class SortByColumnEnum(val code: String) {

    creaetTime("c_create_time"),

    ;

    companion object {

        fun findByName(name: String?): SortByColumnEnum {
            return values().firstOrNull { it.name == name } ?: creaetTime
        }

    }

}