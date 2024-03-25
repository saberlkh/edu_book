package com.edu.book.infrastructure.po.book;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.edu.book.infrastructure.po.BasePo;
import java.util.Date;

/**
 * <p>
 * 图书详情表
 * </p>
 *
 * @author 
 * @since 2024-03-24 22:54:53
 */
@TableName("t_book_detail")
class BookDetailPo : BasePo() {

    /**
     * 业务唯一id
     */
    @TableField("c_uid")
    var uid: String? = null

    /**
     * isbn
     */
    @TableField("c_isbn_code")
    var isbnCode: String? = null

    /**
     * 图书自编码
     */
    @TableField("c_book_uid")
    var bookUid: String? = null

    /**
     * 详情信息
     */
    @TableField("c_detail_info")
    var detailInfo: String? = null

    /**
     * 状态 0 在库 1 借阅中 2 损耗
     */
    @TableField("c_status")
    var status: Int? = null

    /**
     * 入库时间
     */
    @TableField("c_in_storage_time")
    var inStorageTime: Date? = null

    /**
     * 出库时间
     */
    @TableField("c_out_storage_time")
    var outStorageTime: Date? = null

    /**
     * 园区
     */
    @TableField("c_garden")
    var garden: String? = null

}
