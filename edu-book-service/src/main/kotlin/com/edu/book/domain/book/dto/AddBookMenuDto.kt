package com.edu.book.domain.book.dto

import java.io.Serializable
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

/**
 * @Auther: liukaihua
 * @Date: 2024/7/24 20:36
 * @Description:
 */
class AddBookMenuDto: Serializable {

    /**
     * isbn
     */
    @NotEmpty(message = "isbn不能为空")
    var isbns: List<String> = emptyList()

    /**
     * 封面
     */
    @NotBlank(message = "封面不能为空")
    var menuPic: String? = null

    /**
     * 简介
     */
    @NotBlank(message = "简介不能为空")
    var menuDesc: String? = null

    /**
     * 标题
     */
    @NotBlank(message = "标题不能为空")
    var menuTitle: String? = null

    /**
     * 园区uid
     */
    var gardenUid: String? = null

    /**
     * 幼儿园
     */
    @NotBlank(message = "幼儿园Uid不能为空")
    var kindergartenUid: String? = null

}