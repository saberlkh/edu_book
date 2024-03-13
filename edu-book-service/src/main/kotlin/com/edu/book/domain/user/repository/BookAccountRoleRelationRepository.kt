package com.edu.book.domain.user.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.book.infrastructure.po.user.BookAccountRoleRelationPo

/**
 * <p>
 * 账户角色关联表 服务类
 * </p>
 *
 * @author 
 * @since 2024-03-13 22:23:55
 */
interface BookAccountRoleRelationRepository : IService<BookAccountRoleRelationPo> {

    /**
     * 根据账号udi查询
     */
    fun findByAccountUid(accountUid: String?): BookAccountRoleRelationPo?

}
