package com.edu.book.infrastructure.repositoryImpl.area;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.book.domain.area.repository.GardeRepository
import com.edu.book.infrastructure.po.area.GardePo
import com.edu.book.infrastructure.repositoryImpl.dao.area.GardeDao
import org.springframework.stereotype.Repository;

/**
 * 年级表 服务实现类
 * @author 
 * @since 2024-04-10 23:03:42
 */

@Repository
class GardeRepositoryImpl : ServiceImpl<GardeDao, GardePo>(), GardeRepository {

}
