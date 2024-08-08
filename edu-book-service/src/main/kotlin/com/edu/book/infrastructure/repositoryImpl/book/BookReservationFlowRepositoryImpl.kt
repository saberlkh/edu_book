package com.edu.book.infrastructure.repositoryImpl.book;

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtUpdateWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.book.domain.book.enums.ReservationStatusEnum
import com.edu.book.domain.book.repository.BookReservationFlowRepository
import com.edu.book.infrastructure.po.book.BookReservationFlowPo
import com.edu.book.infrastructure.repositoryImpl.dao.book.BookReservationFlowDao
import com.edu.book.infrastructure.util.limitOne
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository;

/**
 * 图书预定表 服务实现类
 * @author 
 * @since 2024-08-08 22:54:07
 */

@Repository
class BookReservationFlowRepositoryImpl : ServiceImpl<BookReservationFlowDao, BookReservationFlowPo>(), BookReservationFlowRepository {

    @Autowired
    private lateinit var bookReservationFlowDao: BookReservationFlowDao

    /**
     * 查询
     */
    override fun queryUserReservationByIsbn(userUid: String, isbn: String): BookReservationFlowPo? {
        val wrapper = KtQueryWrapper(BookReservationFlowPo::class.java)
            .eq(BookReservationFlowPo::reservationUserUid, userUid)
            .eq(BookReservationFlowPo::isbn, isbn)
            .eq(BookReservationFlowPo::reservationStatus, ReservationStatusEnum.Reservationing.status)
            .limitOne()
        return getOne(wrapper)
    }

    /**
     * 修改
     */
    override fun modifyStatusByUid(uid: String, reservationStatus: Int) {
        val wrapper = KtUpdateWrapper(BookReservationFlowPo::class.java)
            .eq(BookReservationFlowPo::uid, uid)
            .set(BookReservationFlowPo::reservationStatus, reservationStatus)
        update(wrapper)
    }

}
