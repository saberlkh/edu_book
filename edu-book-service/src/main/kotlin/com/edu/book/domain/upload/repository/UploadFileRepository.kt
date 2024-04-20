package com.edu.book.domain.upload.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.book.infrastructure.po.upload.UploadFilePo

/**
 * <p>
 * 文件上传表 服务类
 * </p>
 *
 * @author 
 * @since 2024-04-11 22:16:48
 */
interface UploadFileRepository : IService<UploadFilePo> {

    /**
     * 查询
     */
    fun queryUploadFileByFileKey(fileKey: String): UploadFilePo?

    /**
     * 批量查询
     */
    fun batchQuery(fileKeys: List<String>): List<UploadFilePo>?

}
