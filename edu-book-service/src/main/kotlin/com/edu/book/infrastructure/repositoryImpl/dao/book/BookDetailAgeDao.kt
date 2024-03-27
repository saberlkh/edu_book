package com.edu.book.infrastructure.repositoryImpl.dao.book;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edu.book.infrastructure.po.book.BookDetailAgePo
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 图书年龄段 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2024-03-27 20:38:19
 */
@Mapper
@Repository
interface BookDetailAgeDao : BaseMapper<BookDetailAgePo> {

}
