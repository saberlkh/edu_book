package com.edu.book.infrastructure.po.area;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.edu.book.infrastructure.po.BasePo;

/**
 * <p>
 * 园区
 * </p>
 *
 * @author 
 * @since 2024-04-10 23:03:42
 */
@TableName("t_garden")
class GardenPo : BasePo() {

    /**
     * 业务唯一id
     */
    @TableField("c_uid")
    var uid: String? = null

    /**
     * 园区
     */
    @TableField("c_garden_name")
    var gardenName: String? = null

    /**
     * 幼儿园Uid
     */
    @TableField("c_kindergarten_uid")
    var kindergartenUid: String? = null

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
