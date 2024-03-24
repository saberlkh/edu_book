package com.edu.book.application.client

import com.edu.book.api.vo.isbn.GetBookInfoByIsbnRespDto

/**
 * @Auther: liukaihua
 * @Date: 2024/3/24 22:59
 * @Description:
 */
interface IsbnApi {

    /**
     * 通过isbn查询
     */
    fun getBookInfoByIsbnCode(isbnCode: String): GetBookInfoByIsbnRespDto?

}