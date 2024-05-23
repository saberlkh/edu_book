package com.edu.book.domain.user.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.book.infrastructure.po.user.BookUserPo

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author 
 * @since 2024-03-13 22:23:55
 */
interface BookUserRepository : IService<BookUserPo> {

    /**
     * 获取用户
     */
    fun findUserByOpenId(openId: String): BookUserPo?

    /**
     * 修改用户信息
     */
    fun updateUserPoByUid(po: BookUserPo)

    /**
     * 根据用户uid查询用户
     */
    fun findByUserUid(userUid: String): BookUserPo?

    /**
     * 通过手机号查询
     */
    fun findByPhone(phone: String?): BookUserPo?

}
