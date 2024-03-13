package com.edu.book.infrastructure.po.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.edu.book.infrastructure.po.BasePo;

/**
 * <p>
 * 权限基础表
 * </p>
 *
 * @author 
 * @since 2024-03-13 22:23:55
 */
@TableName("t_book_permission_basic")
class BookPermissionBasicPo : BasePo() {

    /**
     * 业务唯一id
     */
    @TableField("c_uid")
    var uid: String? = null

    /**
     * 权限名称
     */
    @TableField("c_permission_name")
    var permissionName: String? = null

    /**
     * 权限编码
     */
    @TableField("c_permission_code")
    var permissionCode: String? = null

}
