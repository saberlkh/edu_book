package com.edu.book.infrastructure.repositoryImpl.upload;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.book.domain.upload.repository.UploadFileRepository
import com.edu.book.infrastructure.po.upload.UploadFilePo
import com.edu.book.infrastructure.repositoryImpl.dao.upload.UploadFileDao
import org.springframework.stereotype.Repository;

/**
 * 文件上传表 服务实现类
 * @author 
 * @since 2024-04-11 22:16:48
 */

@Repository
class UploadFileRepositoryImpl : ServiceImpl<UploadFileDao, UploadFilePo>(), UploadFileRepository {

}
