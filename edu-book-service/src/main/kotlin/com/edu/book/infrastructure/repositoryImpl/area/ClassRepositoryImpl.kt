package com.edu.book.infrastructure.repositoryImpl.area;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.book.domain.area.repository.ClassRepository
import com.edu.book.infrastructure.po.area.ClassPo
import com.edu.book.infrastructure.repositoryImpl.dao.area.ClassDao
import org.springframework.stereotype.Repository;

/**
 * 班级表 服务实现类
 * @author 
 * @since 2024-04-10 23:03:42
 */

@Repository
class ClassRepositoryImpl : ServiceImpl<ClassDao, ClassPo>(), ClassRepository {

}
