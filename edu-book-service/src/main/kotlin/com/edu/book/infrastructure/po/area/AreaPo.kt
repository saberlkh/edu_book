package com.edu.book.infrastructure.po.area;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.edu.book.infrastructure.po.BasePo;

/**
 * <p>
 * 地区表
 * </p>
 *
 * @author 
 * @since 2024-04-10 23:03:42
 */
@TableName("t_area")
class AreaPo : BasePo() {

    /**
     * 业务唯一id
     */
    @TableField("c_uid")
    var uid: String? = null

    /**
     * 地区编码
     */
    @TableField("c_area_code")
    var areaCode: String? = null

    /**
     * 地区名称
     */
    @TableField("c_area_name")
    var areaName: String? = null

    /**
     * 0 省 1 市 2 区
     */
    @TableField("c_area_type")
    var areaType: Int? = null

    /**
     * 父级Uid
     */
    @TableField("c_parent_uid")
    var parentUid: String? = null

}
