package com.edu.book.infrastructure.repositoryImpl.dao.book;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edu.book.infrastructure.po.book.BookDetailClassifyPo
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 图书分类表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2024-03-25 23:01:25
 */
@Mapper
@Repository
interface BookDetailClassifyDao : BaseMapper<BookDetailClassifyPo> {

}
