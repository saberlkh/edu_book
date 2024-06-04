package com.edu.book.infrastructure.repositoryImpl.dao.read;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edu.book.domain.read.dto.PageQueryReadCircleParam
import com.edu.book.infrastructure.po.read.BookReadCirclePo
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 阅读圈表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2024-06-03 22:47:56
 */
@Mapper
@Repository
interface BookReadCircleDao : BaseMapper<BookReadCirclePo> {

    /**
     * 查询个数
     */
    fun getReadCircleTotalCount(param: PageQueryReadCircleParam): Int

    /**
     * 查询列表
     */
    fun pageQueryReadCircles(param: PageQueryReadCircleParam): List<BookReadCirclePo>

}
