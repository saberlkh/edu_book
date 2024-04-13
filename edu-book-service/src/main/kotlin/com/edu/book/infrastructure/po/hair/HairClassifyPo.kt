package com.edu.book.infrastructure.po.hair;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.edu.book.infrastructure.po.BasePo;

/**
 * <p>
 * 美发分类表
 * </p>
 *
 * @author 
 * @since 2024-04-13 16:28:10
 */
@TableName("t_hair_classify")
class HairClassifyPo : BasePo() {

    /**
     * 业务唯一id
     */
    @TableField("c_uid")
    var uid: String? = null

    /**
     * 分类名称
     */
    @TableField("c_classify_name")
    var classifyName: String? = null

    /**
     * 分类封面
     */
    @TableField("c_classify_cover_url")
    var classifyCoverUrl: String? = null

}
