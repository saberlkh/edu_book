package com.edu.book.infrastructure.repositoryImpl.book;

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtUpdateWrapper
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.book.domain.book.dto.PageQueryBookDto
import com.edu.book.domain.book.dto.PageQueryBookResultEntity
import com.edu.book.domain.book.repository.BookRepository
import com.edu.book.infrastructure.po.book.BookPo
import com.edu.book.infrastructure.repositoryImpl.dao.book.BookDao
import com.edu.book.infrastructure.util.limitOne
import org.apache.commons.lang3.math.NumberUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository;

/**
 * 图书表 服务实现类
 * @author 
 * @since 2024-03-24 22:54:53
 */

@Repository
class BookRepositoryImpl : ServiceImpl<BookDao, BookPo>(), BookRepository {

    @Autowired
    private lateinit var bookDao: BookDao

    /**
     * 查询isbn列表
     */
    override fun findIsbnList(garden: String?, isbn: String?): List<BookPo> {
        val wrapper = KtQueryWrapper(BookPo::class.java)
            .eq(!garden.isNullOrBlank(), BookPo::garden, garden)
            .likeRight(!isbn.isNullOrBlank(), BookPo::isbnCode, isbn)
        return bookDao.selectList(wrapper)
    }

    /**
     * 根据isbn查询
     */
    override fun findByIsbnCode(isbnCode: String?): BookPo? {
        if (isbnCode.isNullOrBlank()) return null
        val wrapper = KtQueryWrapper(BookPo::class.java)
            .eq(BookPo::isbnCode, isbnCode)
            .limitOne()
        return getOne(wrapper)
    }

    /**
     * 更新
     */
    override fun updateByUid(po: BookPo) {
        val updateWrapper = KtUpdateWrapper(BookPo::class.java)
            .eq(BookPo::uid, po.uid)
        update(po, updateWrapper)
    }

    /**
     * 分页查询
     */
    override fun pageQueryBooks(dto: PageQueryBookDto): Page<PageQueryBookResultEntity> {
        val total = bookDao.getPageTotal(dto)
        if (total <= NumberUtils.INTEGER_ZERO) return Page()
        val result = bookDao.getPage(dto)
        val page = Page<PageQueryBookResultEntity>(dto.page.toLong(), dto.pageSize.toLong(), total.toLong())
        page.records = result
        return page
    }

}
