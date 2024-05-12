package com.edu.book.infrastructure.repositoryImpl.area;

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.book.domain.area.repository.AreaRepository
import com.edu.book.infrastructure.po.area.AreaPo
import com.edu.book.infrastructure.repositoryImpl.dao.area.AreaDao
import com.edu.book.infrastructure.util.limitOne
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository;

/**
 * 地区表 服务实现类
 * @author 
 * @since 2024-04-10 23:03:42
 */

@Repository
class AreaRepositoryImpl : ServiceImpl<AreaDao, AreaPo>(), AreaRepository {

    @Autowired
    private lateinit var areaDao: AreaDao

    /**
     * 获取地区列表
     */
    override fun queryByParentUid(parentUid: String?, areaType: Int?): List<AreaPo>? {
        val wrapper = KtQueryWrapper(AreaPo::class.java)
            .eq(areaType != null, AreaPo::areaType, areaType)
            .eq(!parentUid.isNullOrBlank(), AreaPo::parentUid, parentUid)
        return areaDao.selectList(wrapper)
    }

    /**
     * 根据区域码查询
     */
    override fun queryByAreaCode(areaCode: String?): AreaPo? {
        if (areaCode.isNullOrBlank()) return null
        val wrapper = KtQueryWrapper(AreaPo::class.java)
            .eq(AreaPo::areaCode, areaCode)
            .limitOne()
        return areaDao.selectOne(wrapper)
    }

    /**
     * 批量查询
     */
    override fun batchQueryByAreaCode(areaCodes: List<String>): List<AreaPo>? {
        if (areaCodes.isNullOrEmpty()) return emptyList()
        val wrapper = KtQueryWrapper(AreaPo::class.java)
            .`in`(AreaPo::areaCode, areaCodes)
        return areaDao.selectList(wrapper)
    }

}
