package com.edu.book.domain.book.repository;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.book.domain.book.dto.ReservationBookPageQueryDto
import com.edu.book.infrastructure.po.book.BookReservationFlowPo

/**
 * <p>
 * 图书预定表 服务类
 * </p>
 *
 * @author 
 * @since 2024-08-08 22:54:07
 */
interface BookReservationFlowRepository : IService<BookReservationFlowPo> {

    /**
     * 查询预订记录
     */
    fun queryUserReservationByIsbn(userUid: String, isbn: String): BookReservationFlowPo?

    /**
     * 删除图书预订记录
     */
    fun deleteUserReservation(userUid: String, isbn: String)

    /**
     * 修改
     */
    fun modifyStatusByUid(uid: String, reservationStatus: Int)

    /**
     * 分页查询
     */
    fun pageQueryReservation(dto: ReservationBookPageQueryDto): Page<BookReservationFlowPo>

}
