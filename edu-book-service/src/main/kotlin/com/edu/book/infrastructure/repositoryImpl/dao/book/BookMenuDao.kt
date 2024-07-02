package com.edu.book.infrastructure.repositoryImpl.dao.book;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edu.book.infrastructure.po.book.BookMenuPo
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 书单表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2024-07-02 23:32:18
 */
@Mapper
@Repository
interface BookMenuDao : BaseMapper<BookMenuPo> {

}
