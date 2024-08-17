package com.edu.book.infrastructure.repositoryImpl.dao.book;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edu.book.domain.book.dto.ChoicenessPageQueryDto
import com.edu.book.infrastructure.po.book.BookDetailPo
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 图书详情表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2024-03-24 22:54:53
 */
@Mapper
@Repository
interface BookDetailDao : BaseMapper<BookDetailPo> {

    /**
     * 查询数量
     */
    fun getTotalCountChoiceness(dto: ChoicenessPageQueryDto): Int

    /**
     * 查询列表
     */
    fun getListCountChoiceness(dto: ChoicenessPageQueryDto): List<BookDetailPo>

}
