package com.edu.book.infrastructure.repositoryImpl.dao.book;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edu.book.domain.book.dto.ReservationBookPageQueryDto
import com.edu.book.infrastructure.po.book.BookReservationFlowPo
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 图书预定表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2024-08-08 22:54:07
 */
@Mapper
@Repository
interface BookReservationFlowDao : BaseMapper<BookReservationFlowPo> {

    /**
     * 查询数量
     */
    fun pageQueryReservationCount(dto: ReservationBookPageQueryDto): Int

    /**
     * 分页查询
     */
    fun pageQueryReservation(dto: ReservationBookPageQueryDto): List<BookReservationFlowPo>

}
