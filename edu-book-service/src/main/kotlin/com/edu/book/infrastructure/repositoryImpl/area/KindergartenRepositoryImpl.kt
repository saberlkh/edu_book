package com.edu.book.infrastructure.repositoryImpl.area;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.book.domain.area.repository.KindergartenRepository
import com.edu.book.infrastructure.po.area.KindergartenPo
import com.edu.book.infrastructure.repositoryImpl.dao.area.KindergartenDao
import org.springframework.stereotype.Repository;

/**
 * 幼儿园表 服务实现类
 * @author 
 * @since 2024-04-10 23:03:42
 */

@Repository
class KindergartenRepositoryImpl : ServiceImpl<KindergartenDao, KindergartenPo>(), KindergartenRepository {

}
