package com.edu.book.infrastructure.repositoryImpl.book;

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtUpdateWrapper
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.book.domain.book.dto.ChoicenessPageQueryDto
import com.edu.book.domain.book.repository.BookDetailRepository
import com.edu.book.infrastructure.po.book.BookDetailPo
import com.edu.book.infrastructure.repositoryImpl.dao.book.BookDetailDao
import com.edu.book.infrastructure.util.limitOne
import org.apache.commons.lang3.math.NumberUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository;

/**
 * 图书详情表 服务实现类
 * @author 
 * @since 2024-03-24 22:54:53
 */

@Repository
class BookDetailRepositoryImpl : ServiceImpl<BookDetailDao, BookDetailPo>(), BookDetailRepository {

    @Autowired
    private lateinit var bookDetailDao: BookDetailDao

    /**
     * 通过园区查询
     */
    override fun findByGardenUid(gardenUid: String): List<BookDetailPo>? {
        val wrapper = KtQueryWrapper(BookDetailPo::class.java)
            .eq(BookDetailPo::gardenUid, gardenUid)
        return bookDetailDao.selectList(wrapper)
    }

    /**
     * 更新
     */
    override fun updateByBookUid(po: BookDetailPo, bookUid: String) {
        val wrapper = KtUpdateWrapper(BookDetailPo::class.java)
            .eq(BookDetailPo::bookUid, bookUid)
        bookDetailDao.update(po, wrapper)
    }

    /**
     * 修改图书装填
     */
    override fun updateBookStatus(bookUid: String, bookStatus: Int) {
        val wrapper = KtUpdateWrapper(BookDetailPo::class.java)
            .set(BookDetailPo::status, bookStatus)
            .eq(BookDetailPo::bookUid, bookUid)
        update(wrapper)
    }

    /**
     * 更新收藏数
     */
    override fun modifyBookCollectCount(bookUid: String, collectCount: Int) {
        val wrapper = KtUpdateWrapper(BookDetailPo::class.java)
            .set(BookDetailPo::collectCount, collectCount)
            .eq(BookDetailPo::bookUid, bookUid)
        update(wrapper)
    }

    /**
     * 修改图书园区
     */
    override fun batchModifyBookDetailGarden(bookUids: List<String>, gardenUid: String) {
        val wrapper = KtUpdateWrapper(BookDetailPo::class.java)
            .`in`(BookDetailPo::bookUid, bookUids)
            .set(BookDetailPo::gardenUid, gardenUid)
        update(wrapper)
    }

    /**
     * 分页查询图书精选
     */
    override fun pageQueryBookChoiceness(dto: ChoicenessPageQueryDto): Page<BookDetailPo> {
        val totalCount = bookDetailDao.getTotalCountChoiceness(dto)
        if (totalCount <= NumberUtils.INTEGER_ZERO) return Page()
        val pageQuery = bookDetailDao.getListCountChoiceness(dto)
        val page = Page<BookDetailPo>(dto.page.toLong(), dto.pageSize.toLong(), totalCount.toLong())
        page.records = pageQuery
        return page
    }

    /**
     * 查询
     */
    override fun findByBookUid(bookUid: String): BookDetailPo? {
        val wrapper = KtQueryWrapper(BookDetailPo::class.java)
            .eq(BookDetailPo::bookUid, bookUid)
            .limitOne()
        return getOne(wrapper)
    }

    /**
     * 批量查询
     */
    override fun findByBookUids(bookUids: List<String>): List<BookDetailPo>? {
        if (bookUids.isNullOrEmpty()) return emptyList()
        val wrapper = KtQueryWrapper(BookDetailPo::class.java)
            .`in`(BookDetailPo::bookUid, bookUids)
        return bookDetailDao.selectList(wrapper)
    }

    /**
     * 删除
     */
    override fun deleteByBookUid(bookUid: String) {
        val wrapper = KtUpdateWrapper(BookDetailPo::class.java)
            .eq(BookDetailPo::bookUid, bookUid)
        remove(wrapper)
    }

}
