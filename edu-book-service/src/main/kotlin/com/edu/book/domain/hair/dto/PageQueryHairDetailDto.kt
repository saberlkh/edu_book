package com.edu.book.domain.hair.dto

import com.edu.book.infrastructure.util.page.Page
import com.edu.book.infrastructure.util.page.PageUtil
import java.io.Serializable

/**
 * @Auther: liukaihua
 * @Date: 2024/4/13 23:36
 * @Description:
 */
class PageQueryHairDetailDto: Page<HairClassifyFileDto>, Serializable {

    var classifyUid: String = ""

    var classifyName: String = ""

    var classifyCoverUrl: String = ""

    constructor(){}

    constructor(page: Int, pageSize: Int, totalCount: Int, result: List<HairClassifyFileDto>?, classifyUid: String, classifyName: String, classifyCoverUrl: String) {
        this.page = page
        this.result = result
        this.pageSize = pageSize
        this.totalCount = totalCount
        thisPage = PageUtil.computePage(page, pageSize, totalCount)
        this.classifyName = classifyName
        this.classifyCoverUrl = classifyCoverUrl
        this.classifyUid = classifyUid
    }

}