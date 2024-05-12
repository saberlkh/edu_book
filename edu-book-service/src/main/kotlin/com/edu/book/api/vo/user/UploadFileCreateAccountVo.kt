package com.edu.book.api.vo.user

import cn.afterturn.easypoi.excel.annotation.Excel
import java.io.Serializable

/**
 * @Auther: liukaihua
 * @Date: 2024/5/12 22:17
 * @Description:
 */
class UploadFileCreateAccountVo: Serializable {

    /**
     * 学生名称
     */
    @Excel(name = "学生姓名", needMerge = true)
    var studentName: String = ""

    /**
     * 是否开通借阅服务
     */
    @Excel(name = "是否购买借阅服务", needMerge = true)
    var openBorrowService: String = ""

    /**
     * 家长手机号
     */
    @Excel(name = "家长手机号", needMerge = true)
    var parentPhone: String = ""

}