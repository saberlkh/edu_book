package com.edu.book.domain.hair.repository;

import com.baomidou.mybatisplus.extension.service.IService
import com.edu.book.infrastructure.po.hair.HairClassifyFilePo

/**
 * <p>
 * 美发分类文件表 服务类
 * </p>
 *
 * @author 
 * @since 2024-04-13 16:28:10
 */
interface HairClassifyFileRepository : IService<HairClassifyFilePo> {

    /**
     * 删除
     */
    fun deleteByClassifyUid(classifyUid: String)

    /**
     * 查询
     */
    fun getByClassifyUid(classifyUid: String): List<HairClassifyFilePo>

}
