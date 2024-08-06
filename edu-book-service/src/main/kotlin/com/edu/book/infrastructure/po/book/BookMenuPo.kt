package com.edu.book.infrastructure.po.book;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.edu.book.infrastructure.po.BasePo;

/**
 * <p>
 * 书单表
 * </p>
 *
 * @author 
 * @since 2024-07-02 23:32:18
 */
@TableName("t_book_menu")
class BookMenuPo : BasePo() {

    /**
     * 业务唯一id
     */
    @TableField("c_uid")
    var uid: String? = null

    /**
     * pic
     */
    @TableField("c_menu_pic")
    var menuPic: String? = null

    /**
     * c_menu_title
     */
    @TableField("c_menu_title")
    var menuTitle: String? = null

    /**
     * c_menu_desc
     */
    @TableField("c_menu_desc")
    var menuDesc: String? = null

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
