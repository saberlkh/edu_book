package com.edu.book.infrastructure.repositoryImpl.area;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.book.domain.area.repository.LevelRepository
import com.edu.book.infrastructure.po.area.LevelPo
import com.edu.book.infrastructure.repositoryImpl.dao.area.LevelDao
import org.springframework.stereotype.Repository;

/**
 * 层级表 服务实现类
 * @author 
 * @since 2024-05-09 11:10:10
 */

@Repository
class LevelRepositoryImpl : ServiceImpl<LevelDao, LevelPo>(), LevelRepository {

}
