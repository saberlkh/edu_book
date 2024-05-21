package com.edu.book.domain.book.enums

import org.apache.commons.lang3.StringUtils

/**
 * @Auther: liukaihua
 * @Date: 2024/3/27 20:28
 * @Description:
 */
enum class BookClassifyEnum(val code: String, val desc: String) {

    Emotional_Enlightenment("Emotional_Enlightenment", "情感启蒙"),

    Language_Enlightenment("Language_Enlightenment", "语言启蒙"),

    Taste_Cognition("Taste_Cognition", "趣味认知"),

    Mathematical_Logic("Mathematical_Logic", "数理逻辑"),

    Emotion_Management("Emotion_Management", "情绪管理"),

    Science_Popularization_Nature("Science_Popularization_Nature", "自然科普"),

    Fantastic_Imagination("Fantastic_Imagination", "奇幻想象"),

    Family_Picture_Book("Family_Picture_Book", "亲子绘本"),

    Bilingual_Picture_Book("Bilingual_Picture_Book", "双语绘本"),

    Winning_Picture_Book("Winning_Picture_Book", "获奖绘本"),

    ;

    companion object {

        fun getDescByCode(code: String): String {
            return BookClassifyEnum.values().toList().filter { StringUtils.equals(it.code, code) }.firstOrNull()?.desc ?: ""
        }

    }

}