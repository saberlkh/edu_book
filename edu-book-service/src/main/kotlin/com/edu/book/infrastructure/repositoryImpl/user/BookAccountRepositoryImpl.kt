package com.edu.book.infrastructure.repositoryImpl.user;

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.book.domain.user.repository.BookAccountRepository
import com.edu.book.infrastructure.po.user.BookAccountPo
import com.edu.book.infrastructure.repositoryImpl.dao.user.BookAccountDao
import com.edu.book.infrastructure.util.limitOne
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository;

/**
 * 账号表 服务实现类
 * @author 
 * @since 2024-03-13 22:23:55
 */

@Repository
class BookAccountRepositoryImpl : ServiceImpl<BookAccountDao, BookAccountPo>(), BookAccountRepository {

    @Autowired
    private lateinit var bookAccountDao: BookAccountDao

    override fun findByUid(accountUid: String?): BookAccountPo? {
        if (accountUid.isNullOrBlank()) return null
        val wrapper = KtQueryWrapper(BookAccountPo::class.java)
            .eq(BookAccountPo::accountUid, accountUid)
            .limitOne()
        return getOne(wrapper)
    }

    /**
     * 通过家长手机号查询
     */
    override fun findByParentPhone(parentPhones: List<String>): List<BookAccountPo>? {
        if (parentPhones.isNullOrEmpty()) return emptyList()
        val wrapper = KtQueryWrapper(BookAccountPo::class.java)
            .`in`(BookAccountPo::parentPhone, parentPhones)
        return bookAccountDao.selectList(wrapper)
    }

}
