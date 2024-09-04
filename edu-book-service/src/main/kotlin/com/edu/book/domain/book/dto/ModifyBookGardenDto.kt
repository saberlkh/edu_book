package com.edu.book.domain.book.dto

import java.io.Serializable
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

/**
 * @Auther: liukaihua
 * @Date: 2024/9/4 22:17
 * @Description:
 */
class ModifyBookGardenDto: Serializable {

    /**
     * 图书uid
     */
    @NotEmpty(message = "图书uid不能为空")
    var bookUids: List<String> = emptyList()

    /**
     * 园区Uid
     */
    @NotBlank(message = "园区Uid不能为空")
    var gardenUid: String = ""

}