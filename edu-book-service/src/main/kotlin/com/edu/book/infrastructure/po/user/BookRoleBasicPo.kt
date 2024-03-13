package com.edu.book.infrastructure.po.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.edu.book.infrastructure.po.BasePo;

/**
 * <p>
 * 角色基础表
 * </p>
 *
 * @author 
 * @since 2024-03-13 22:23:55
 */
@TableName("t_book_role_basic")
class BookRoleBasicPo : BasePo() {

    /**
     * 业务唯一id
     */
    @TableField("c_uid")
    var uid: String? = null

    /**
     * 角色名称
     */
    @TableField("c_role_name")
    var roleName: String? = null

    /**
     * 角色编码
     */
    @TableField("c_role_code")
    var roleCode: String? = null

}
