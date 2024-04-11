package com.edu.book.infrastructure.po.area;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.edu.book.infrastructure.po.BasePo;

/**
 * <p>
 * 幼儿园表
 * </p>
 *
 * @author 
 * @since 2024-04-10 23:03:42
 */
@TableName("t_kindergarten")
class KindergartenPo : BasePo() {

    /**
     * 业务唯一id
     */
    @TableField("c_uid")
    var uid: String? = null

    /**
     * 幼儿园名称
     */
    @TableField("c_kindergarten_name")
    var kindergartenName: String? = null

    /**
     * 省Id
     */
    @TableField("c_province_id")
    var provinceId: String? = null

    /**
     * 市Id
     */
    @TableField("c_city_id")
    var cityId: String? = null

    /**
     * 区Id
     */
    @TableField("c_district_id")
    var districtId: String? = null

}
