package com.edu.book.infrastructure.util

import java.util.Random
import org.apache.commons.lang3.math.NumberUtils

/**
 * @Auther: liukaihua
 * @Date: 2024/5/14 22:42
 * @Description:
 */
object RandomUtil {

    /**
     * 获取随机数字
     */
    fun getRandomNum(length: Int): String {
        val rand = Random()
        var result = ""
        for (i in NumberUtils.INTEGER_ZERO until length) {
            result += rand.nextInt(10)
        }
        return result
    }

}