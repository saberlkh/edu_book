package com.edu.book.infrastructure.repositoryImpl.read;

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.book.domain.read.dto.PageQueryReadCircleParam
import com.edu.book.domain.read.repository.BookReadCircleRepository
import com.edu.book.infrastructure.po.read.BookReadCirclePo
import com.edu.book.infrastructure.repositoryImpl.dao.read.BookReadCircleDao
import com.edu.book.infrastructure.util.limitOne
import org.apache.commons.lang3.math.NumberUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository;

/**
 * 阅读圈表 服务实现类
 * @author 
 * @since 2024-06-03 22:47:56
 */

@Repository
class BookReadCircleRepositoryImpl : ServiceImpl<BookReadCircleDao, BookReadCirclePo>(), BookReadCircleRepository {

    @Autowired
    private lateinit var bookReadCircleDao: BookReadCircleDao

    /**
     * 分页查询
     */
    override fun pageQueryReadCircle(param: PageQueryReadCircleParam): Page<BookReadCirclePo> {
        val totalCount = bookReadCircleDao.getReadCircleTotalCount(param)
        if (totalCount <= NumberUtils.INTEGER_ZERO) return Page()
        val result = bookReadCircleDao.pageQueryReadCircles(param)
        val page = Page<BookReadCirclePo>(param.page.toLong(), param.pageSize.toLong(), totalCount.toLong())
        page.records = result
        return page
    }

    /**
     * 查询
     */
    override fun getByUid(circleUid: String): BookReadCirclePo? {
        val wrapper = KtQueryWrapper(BookReadCirclePo::class.java)
            .eq(BookReadCirclePo::uid, circleUid)
            .limitOne()
        return bookReadCircleDao.selectOne(wrapper)
    }

}
