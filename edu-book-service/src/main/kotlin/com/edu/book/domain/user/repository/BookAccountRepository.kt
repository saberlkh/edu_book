package com.edu.book.domain.user.repository;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.book.domain.user.dto.PageQueryAccountDto
import com.edu.book.domain.user.dto.PageQueryAccountParamDto
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
     * 批量查询
     */
    fun batchQueryByAccountUids(accountUids: List<String>): List<BookAccountPo>?

    /**
     * 通过家长手机号查询
     */
    fun findByParentPhone(parentPhones: List<String>): List<BookAccountPo>?

    /**
     * 分页查询
     */
    fun pageQuery(param: PageQueryAccountParamDto): Page<BookAccountPo>

    /**
     * 通过借阅卡Id查询
     */
    fun findByBorrwoCardId(borrowCardId: String): BookAccountPo?

}
