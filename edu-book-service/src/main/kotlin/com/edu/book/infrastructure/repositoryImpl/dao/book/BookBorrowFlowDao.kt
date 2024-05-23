package com.edu.book.infrastructure.repositoryImpl.dao.book;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edu.book.domain.book.dto.PageQueryBorrowBookDto
import com.edu.book.infrastructure.po.book.BookBorrowFlowPo
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 图书借阅流水表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2024-03-24 22:54:53
 */
@Mapper
@Repository
interface BookBorrowFlowDao : BaseMapper<BookBorrowFlowPo> {

    /**
     * 查询个数
     */
    fun getBorrowFlowTotal(dto: PageQueryBorrowBookDto): Int

    /**
     * 分页查询
     */
    fun getPageBorrowFlow(dto: PageQueryBorrowBookDto): List<BookBorrowFlowPo>

}
