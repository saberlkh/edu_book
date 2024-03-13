package com.edu.book.domain.user.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.book.infrastructure.po.user.BookRolePermissionRelationPo

/**
 * <p>
 * 角色权限关联表 服务类
 * </p>
 *
 * @author 
 * @since 2024-03-13 22:23:55
 */
interface BookRolePermissionRelationRepository : IService<BookRolePermissionRelationPo> {

    /**
     * 获取权限列表
     */
    fun findListByRoleUid(roleUid: String?): List<BookRolePermissionRelationPo>

}
