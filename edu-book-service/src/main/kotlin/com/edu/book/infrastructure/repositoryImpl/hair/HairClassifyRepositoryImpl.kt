package com.edu.book.infrastructure.repositoryImpl.hair;

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtUpdateWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.book.domain.hair.repository.HairClassifyRepository
import com.edu.book.infrastructure.po.hair.HairClassifyPo
import com.edu.book.infrastructure.repositoryImpl.dao.hair.HairClassifyDao
import com.edu.book.infrastructure.util.limitOne
import org.springframework.stereotype.Repository;

/**
 * 美发分类表 服务实现类
 * @author 
 * @since 2024-04-13 16:28:10
 */

@Repository
class HairClassifyRepositoryImpl : ServiceImpl<HairClassifyDao, HairClassifyPo>(), HairClassifyRepository {

    /**
     * 更新
     */
    override fun updateByUid(po: HairClassifyPo) {
        val wrapper = KtUpdateWrapper(HairClassifyPo::class.java)
            .eq(HairClassifyPo::uid, po.uid)
        update(po, wrapper)
    }

    /**
     * 查询
     */
    override fun queryByUid(classifyUid: String): HairClassifyPo? {
        val wrapper = KtQueryWrapper(HairClassifyPo::class.java)
            .eq(HairClassifyPo::uid, classifyUid)
            .limitOne()
        return getOne(wrapper)
    }

    /**
     * 删除
     */
    override fun deleteByUid(classifyUid: String) {
        val wrapper = KtUpdateWrapper(HairClassifyPo::class.java)
            .eq(HairClassifyPo::uid, classifyUid)
        remove(wrapper)
    }

}
