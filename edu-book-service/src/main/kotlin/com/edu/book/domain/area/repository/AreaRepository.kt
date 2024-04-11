package com.edu.book.domain.area.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.book.infrastructure.po.area.AreaPo

/**
 * <p>
 * 地区表 服务类
 * </p>
 *
 * @author 
 * @since 2024-04-10 23:03:42
 */
interface AreaRepository : IService<AreaPo> {

    /**
     * 获取地区
     */
    fun queryByAreaType(areaCode: String?, areaType: Int?): List<AreaPo>?

}
