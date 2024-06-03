package com.edu.book.infrastructure.po.read;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.edu.book.infrastructure.po.BasePo;

/**
 * <p>
 * 阅读圈附件表
 * </p>
 *
 * @author 
 * @since 2024-06-03 22:47:56
 */
@TableName("t_book_read_circle_attachment")
class BookReadCircleAttachmentPo : BasePo() {

    /**
     * 业务唯一id
     */
    @TableField("c_uid")
    var uid: String? = null

    /**
     * uid
     */
    @TableField("c_read_circle_uid")
    var readCircleUid: String? = null

    /**
     * fileKey
     */
    @TableField("c_file_key")
    var fileKey: String? = null

}
