package com.edu.book.infrastructure.po.area;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.edu.book.infrastructure.po.BasePo;

/**
 * <p>
 * 班级表
 * </p>
 *
 * @author 
 * @since 2024-04-10 23:03:42
 */
@TableName("t_class")
class ClassPo : BasePo() {

    /**
     * 业务唯一id
     */
    @TableField("c_uid")
    var uid: String? = null

    /**
     * 班级名称
     */
    @TableField("c_class_name")
    var className: String? = null

    /**
     * 年级Uid
     */
    @TableField("c_garde_uid")
    var gardeUid: String? = null

}
