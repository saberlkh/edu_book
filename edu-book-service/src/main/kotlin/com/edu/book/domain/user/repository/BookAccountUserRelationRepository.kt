package com.edu.book.domain.user.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.book.infrastructure.po.user.BookAccountUserRelationPo

/**
 * <p>
 * 账号用户关联表 服务类
 * </p>
 *
 * @author 
 * @since 2024-03-13 22:23:55
 */
interface BookAccountUserRelationRepository : IService<BookAccountUserRelationPo> {

    /**
     * 查询
     */
    fun findByAccountUid(accountUid: String?): BookAccountUserRelationPo?

}
