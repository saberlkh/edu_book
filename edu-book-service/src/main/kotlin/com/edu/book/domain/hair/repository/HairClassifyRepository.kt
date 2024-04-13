package com.edu.book.domain.hair.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.book.infrastructure.po.hair.HairClassifyPo

/**
 * <p>
 * 美发分类表 服务类
 * </p>
 *
 * @author 
 * @since 2024-04-13 16:28:10
 */
interface HairClassifyRepository : IService<HairClassifyPo> {

    /**
     * 查询
     */
    fun queryByUid(classifyUid: String): HairClassifyPo?

    /**
     * 删除
     */
    fun deleteByUid(classifyUid: String)

}
