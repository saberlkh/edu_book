package com.edu.book.domain.book.dto

import java.io.Serializable
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

/**
 * @Auther: liukaihua
 * @Date: 2024/7/28 22:09
 * @Description:
 */
class ModifyBookMenuDto: Serializable {

    /**
     * 书单uid
     */
    @NotBlank(message = "书单Uid不能为空")
    var bookMenuUid: String? = null

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
    @NotBlank(message = "园区Uid")
    var gardenUid: String? = null

}