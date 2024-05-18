package com.edu.book.api.vo.book

import java.io.Serializable
import javax.validation.constraints.NotBlank

/**
 * @Auther: liukaihua
 * @Date: 2024/5/18 22:18
 * @Description:
 */
class BorrowBookVo: Serializable {

    /**
     * 借书卡Id
     */
    @NotBlank(message = "借书卡Id不能为空")
    var borrowCardId: String = ""

    /**
     * 书籍uid
     */
    @NotBlank(message = "书籍Uid不能为空")
    var bookUid: String = ""

}