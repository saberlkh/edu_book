package com.edu.book.infrastructure.repositoryImpl.dao.hair;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edu.book.domain.hair.dto.PageQueryClassifyDetailParam
import com.edu.book.infrastructure.po.hair.HairClassifyFilePo
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 美发分类文件表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2024-04-13 16:28:10
 */
@Mapper
@Repository
interface HairClassifyFileDao : BaseMapper<HairClassifyFilePo> {

    /**
     * 分页查询
     */
    fun queryListByClassifyUid(param: PageQueryClassifyDetailParam): List<HairClassifyFilePo>

}
