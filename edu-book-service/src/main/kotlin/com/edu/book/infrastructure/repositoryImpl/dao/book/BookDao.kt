package com.edu.book.infrastructure.repositoryImpl.dao.book;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edu.book.domain.book.dto.PageQueryBookDto
import com.edu.book.domain.book.dto.PageQueryBookResultEntity
import com.edu.book.infrastructure.po.book.BookPo
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 图书表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2024-03-24 22:54:53
 */
@Mapper
@Repository
interface BookDao : BaseMapper<BookPo> {

    /**
     * 查询总量
     */
    fun getPageTotal(dto: PageQueryBookDto): Int

    /**
     * 分页查询
     */
    fun getPage(dto: PageQueryBookDto): List<PageQueryBookResultEntity>

}
