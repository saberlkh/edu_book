package com.edu.book.infrastructure.po.hair;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.edu.book.infrastructure.po.BasePo;

/**
 * <p>
 * 美发分类文件表
 * </p>
 *
 * @author 
 * @since 2024-04-13 16:28:10
 */
@TableName("t_hair_classify_file")
class HairClassifyFilePo : BasePo() {

    /**
     * 业务唯一id
     */
    @TableField("c_uid")
    var uid: String? = null

    /**
     * 分类uid
     */
    @TableField("c_classify_uid")
    var classifyUid: String? = null

    /**
     * 文件路径
     */
    @TableField("c_file_path")
    var filePath: String? = null

    /**
     * 文件名称
     */
    @TableField("c_file_key")
    var fileKey: String? = null

}
