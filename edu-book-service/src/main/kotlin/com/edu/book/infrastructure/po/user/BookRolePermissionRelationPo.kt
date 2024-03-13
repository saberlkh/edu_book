package com.edu.book.infrastructure.po.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.edu.book.infrastructure.po.BasePo;

/**
 * <p>
 * 角色权限关联表
 * </p>
 *
 * @author 
 * @since 2024-03-13 22:23:55
 */
@TableName("t_book_role_permission_relation")
class BookRolePermissionRelationPo : BasePo() {

    /**
     * 业务唯一id
     */
    @TableField("c_uid")
    var uid: String? = null

    /**
     * 角色Uid
     */
    @TableField("c_role_uid")
    var roleUid: String? = null

    /**
     * 角色编码
     */
    @TableField("c_role_code")
    var roleCode: String? = null

    /**
     * 权限Uid
     */
    @TableField("c_permission_uid")
    var permissionUid: String? = null

    /**
     * 权限编码
     */
    @TableField("c_permission_code")
    var permissionCode: String? = null

}
