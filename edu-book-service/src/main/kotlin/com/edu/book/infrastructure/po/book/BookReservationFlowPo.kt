package com.edu.book.infrastructure.po.book;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.edu.book.infrastructure.po.BasePo;

/**
 * <p>
 * 图书预定表
 * </p>
 *
 * @author 
 * @since 2024-08-08 22:54:07
 */
@TableName("t_book_reservation_flow")
class BookReservationFlowPo : BasePo() {

    /**
     * 业务唯一id
     */
    @TableField("c_uid")
    var uid: String? = null

    /**
     * isbn
     */
    @TableField("c_isbn")
    var isbn: String? = null

    /**
     * 预订人
     */
    @TableField("c_reservation_user_uid")
    var reservationUserUid: String? = null

    /**
     * 0 预定中 1 已借阅
     */
    @TableField("c_reservation_status")
    var reservationStatus: Int? = null

    /**
     * c_garden_uid
     */
    @TableField("c_garden_uid")
    var gardenUid: String? = null

    /**
     * c_kindergarten_uid
     */
    @TableField("c_kindergarten_uid")
    var kindergartenUid: String? = null

}
