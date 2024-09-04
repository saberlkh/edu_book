package com.edu.book.domain.book.repository;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.book.domain.book.dto.ChoicenessPageQueryDto
import com.edu.book.infrastructure.po.book.BookDetailPo

/**
 * <p>
 * 图书详情表 服务类
 * </p>
 *
 * @author 
 * @since 2024-03-24 22:54:53
 */
interface BookDetailRepository : IService<BookDetailPo> {

    /**
     * 查询
     */
    fun findByBookUid(bookUid: String): BookDetailPo?

    /**
     * 批量查询
     */
    fun findByBookUids(bookUids: List<String>): List<BookDetailPo>?

    /**
     * 删除
     */
    fun deleteByBookUid(bookUid: String)

    /**
     * 通过园区查询
     */
    fun findByGardenUid(gardenUid: String): List<BookDetailPo>?

    /**
     * 更新
     */
    fun updateByBookUid(po: BookDetailPo, bookUid: String)

    /**
     * 修改图书状态
     */
    fun updateBookStatus(bookUid: String, bookStatus: Int)

    /**
     * 更新收藏数
     */
    fun modifyBookCollectCount(bookUid: String, collectCount: Int)

    /**
     * 修改图书园区
     */
    fun batchModifyBookDetailGarden(bookUids: List<String>, gardenUid: String)

    /**
     * 分页查询图书精选
     */
    fun pageQueryBookChoiceness(dto: ChoicenessPageQueryDto): Page<BookDetailPo>

}
