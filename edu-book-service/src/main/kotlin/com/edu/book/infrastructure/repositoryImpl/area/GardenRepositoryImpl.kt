package com.edu.book.infrastructure.repositoryImpl.area;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.book.domain.area.repository.GardenRepository
import com.edu.book.infrastructure.po.area.GardenPo
import com.edu.book.infrastructure.repositoryImpl.dao.area.GardenDao
import org.springframework.stereotype.Repository;

/**
 * 园区 服务实现类
 * @author 
 * @since 2024-04-10 23:03:42
 */

@Repository
class GardenRepositoryImpl : ServiceImpl<GardenDao, GardenPo>(), GardenRepository {

}
