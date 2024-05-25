package com.edu.book.infrastructure.repositoryImpl.dao.book;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edu.book.domain.book.dto.PageQueryUserBookCollectParam
import com.edu.book.infrastructure.po.book.BookCollectFlowPo
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 图书收藏表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2024-05-25 20:53:04
 */
@Mapper
@Repository
interface BookCollectFlowDao : BaseMapper<BookCollectFlowPo> {

    /**
     * 查询总数
     */
    fun getBookCollectTotal(param: PageQueryUserBookCollectParam): Int

    /**
     * 查询列表
     */
    fun getPageListBookCollect(param: PageQueryUserBookCollectParam): List<BookCollectFlowPo>

}
