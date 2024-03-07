package com.edu.book.infrastructure.util

import java.util.*
import org.apache.commons.lang3.StringUtils

/**
 * @Auther: liukaihua
 * @Date: 2024/3/7 10:32
 * @Description:
 */
class UUIDUtil {

    init {
        throw UnsupportedOperationException()
    }

    companion object {

        /** 生成UUID值  */
        fun createUUID(): String {
            return UUID.randomUUID().toString().replace("-", "")
        }

        /** 格式化UUID值，去掉“-”符号  */
        fun formatUUID(uuid: String?): String? {
            return if (StringUtils.isNotEmpty(uuid)) {
                uuid!!.replace("-", "")
            } else uuid
        }
    }

}