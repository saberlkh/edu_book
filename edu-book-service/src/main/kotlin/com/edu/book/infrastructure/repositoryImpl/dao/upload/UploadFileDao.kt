package com.edu.book.infrastructure.repositoryImpl.dao.upload;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edu.book.infrastructure.po.upload.UploadFilePo
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 文件上传表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2024-04-11 22:16:48
 */
@Mapper
@Repository
interface UploadFileDao : BaseMapper<UploadFilePo> {

}
