package com.edu.book.domain.read.repository;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.book.domain.read.dto.PageQueryReadCircleParam
import com.edu.book.infrastructure.po.read.BookReadCirclePo

/**
 * <p>
 * 阅读圈表 服务类
 * </p>
 *
 * @author 
 * @since 2024-06-03 22:47:56
 */
interface BookReadCircleRepository : IService<BookReadCirclePo> {

    /**
     * 分页查询
     */
    fun pageQueryReadCircle(param: PageQueryReadCircleParam): Page<BookReadCirclePo>

}
