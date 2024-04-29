package com.edu.book.infrastructure.repositoryImpl.upload;

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtUpdateWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.book.domain.upload.repository.UploadFileRepository
import com.edu.book.infrastructure.po.upload.UploadFilePo
import com.edu.book.infrastructure.repositoryImpl.dao.upload.UploadFileDao
import com.edu.book.infrastructure.util.limitOne
import org.springframework.stereotype.Repository;

/**
 * 文件上传表 服务实现类
 * @author 
 * @since 2024-04-11 22:16:48
 */

@Repository
class UploadFileRepositoryImpl : ServiceImpl<UploadFileDao, UploadFilePo>(), UploadFileRepository {

    /**
     * 批量删除
     */
    override fun batchRemove(fileKeys: List<String>) {
        val wrapper = KtUpdateWrapper(UploadFilePo::class.java)
            .`in`(UploadFilePo::fileKey, fileKeys)
        remove(wrapper)
    }

    /**
     * 查询
     */
    override fun queryUploadFileByFileKey(fileKey: String): UploadFilePo? {
      val wrapper = KtQueryWrapper(UploadFilePo::class.java)
          .eq(UploadFilePo::fileKey, fileKey)
          .limitOne()
        return getOne(wrapper)
    }

    /**
     * 更新
     */
    override fun updateUploadFile(po: UploadFilePo) {
        val wrapper = KtUpdateWrapper(UploadFilePo::class.java)
            .eq(UploadFilePo::uid, po.uid)
        update(po, wrapper)
    }

    /**
     * 批量查询
     */
    override fun batchQuery(fileKeys: List<String>): List<UploadFilePo>? {
        if (fileKeys.isNullOrEmpty()) return emptyList()
        val wrapper = KtQueryWrapper(UploadFilePo::class.java)
            .`in`(UploadFilePo::fileKey, fileKeys)
        return list(wrapper)
    }

}
