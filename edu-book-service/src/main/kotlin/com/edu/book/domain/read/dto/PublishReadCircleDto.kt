package com.edu.book.domain.read.dto

import java.io.Serializable

/**
 * @Auther: liukaihua
 * @Date: 2024/6/3 23:11
 * @Description:
 */
class PublishReadCircleDto: Serializable {

    /**
     * 文本
     */
    var readText: String = ""

    /**
     * 用户Uid
     */
    var userUid: String = ""

    /**
     * 附件
     */
    var attachments: List<String> = emptyList()

}