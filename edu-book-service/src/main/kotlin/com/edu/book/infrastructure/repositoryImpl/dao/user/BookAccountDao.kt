package com.edu.book.infrastructure.repositoryImpl.dao.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edu.book.domain.book.dto.PageQueryBookDto
import com.edu.book.domain.user.dto.PageQueryAccountDto
import com.edu.book.domain.user.dto.PageQueryAccountParamDto
import com.edu.book.infrastructure.po.user.BookAccountPo
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 账号表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2024-03-13 22:23:55
 */
@Mapper
@Repository
interface BookAccountDao : BaseMapper<BookAccountPo> {

    /**
     * 查询总量
     */
    fun getPageTotal(param: PageQueryAccountParamDto): Int

    /**
     * 分页查询
     */
    fun getPage(param: PageQueryAccountParamDto): List<BookAccountPo>

}
