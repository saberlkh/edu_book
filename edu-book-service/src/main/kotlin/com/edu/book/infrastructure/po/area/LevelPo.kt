package com.edu.book.infrastructure.po.area;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.edu.book.infrastructure.po.BasePo;

/**
 * <p>
 * 层级表
 * </p>
 *
 * @author 
 * @since 2024-05-09 11:10:10
 */
@TableName("t_level")
class LevelPo : BasePo() {

    /**
     * 业务唯一id
     */
    @TableField("c_uid")
    var uid: String? = null

    /**
     * 班级名称
     */
    @TableField("c_level_name")
    var levelName: String? = null

    /**
     * 0 幼儿园 1 园区 2 年级 3 班级
     */
    @TableField("c_level_type")
    var levelType: Int? = null

    /**
     * 父级Uid
     */
    @TableField("c_parent_uid")
    var parentUid: String? = null

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
