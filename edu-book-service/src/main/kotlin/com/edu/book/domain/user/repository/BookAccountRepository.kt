package com.edu.book.domain.user.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.book.infrastructure.po.user.BookAccountPo

/**
 * <p>
 * 账号表 服务类
 * </p>
 *
 * @author 
 * @since 2024-03-13 22:23:55
 */
interface BookAccountRepository : IService<BookAccountPo> {

    /**
     * 查询
     */
    fun findByUid(accountUid: String?): BookAccountPo?

    /**
     * 通过家长手机号查询
     */
    fun findByParentPhone(parentPhones: List<String>): List<BookAccountPo>?

}
