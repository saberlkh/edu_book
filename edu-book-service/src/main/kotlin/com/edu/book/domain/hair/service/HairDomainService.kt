package com.edu.book.domain.hair.service

import com.edu.book.domain.hair.dto.HairClassifyDto
import com.edu.book.domain.hair.dto.HairClassifyFileDto
import com.edu.book.domain.hair.dto.ModifyClassifyDto
import com.edu.book.domain.hair.dto.PageQueryClassifyDetailParam
import com.edu.book.domain.hair.dto.PageQueryHairDetailDto
import com.edu.book.domain.hair.dto.SaveHairClassifyDto
import com.edu.book.domain.hair.repository.HairClassifyFileRepository
import com.edu.book.domain.hair.repository.HairClassifyRepository
import com.edu.book.infrastructure.po.hair.HairClassifyFilePo
import com.edu.book.infrastructure.po.hair.HairClassifyPo
import com.edu.book.infrastructure.util.MapperUtil
import com.edu.book.infrastructure.util.QiNiuUtil
import com.edu.book.infrastructure.util.UUIDUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.apache.commons.lang3.math.NumberUtils
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @Auther: liukaihua
 * @Date: 2024/4/13 16:50
 * @Description:
 */

@Service
class HairDomainService {

    private val logger = LoggerFactory.getLogger(HairDomainService::class.java)

    @Autowired
    private lateinit var hairClassifyFileRepository: HairClassifyFileRepository

    @Autowired
    private lateinit var hairClassifyRepository: HairClassifyRepository

    @Autowired
    private lateinit var qiNiuUtil: QiNiuUtil

    /**
     * 编辑分类
     */
    @Transactional(rollbackFor = [Exception::class])
    fun modifyClassify(dto: ModifyClassifyDto) {
        //查询分类信息
        val classifyPo = hairClassifyRepository.queryByUid(dto.classifyUid) ?: return
        //编辑分类信息
        val modifyClassifyPo = MapperUtil.map(HairClassifyPo::class.java, dto).apply {
            this.uid = dto.classifyUid
        }
        hairClassifyRepository.updateByUid(modifyClassifyPo)
        //查询当前文件列表
        val filePos = hairClassifyFileRepository.getByClassifyUid(dto.classifyUid)
        if (filePos.isNullOrEmpty()) {
            val saveFilePos = dto.files.map {
                MapperUtil.map(HairClassifyFilePo::class.java, it).apply {
                    this.uid = UUIDUtil.createUUID()
                    this.classifyUid = dto.classifyUid
                }
            }
            if (!saveFilePos.isNullOrEmpty()) {
                hairClassifyFileRepository.saveBatch(saveFilePos)
            }
            return
        }
        //删除文件
        val deleteFilePos = filePos.filter { !dto.files.mapNotNull { it.fileKey }.contains(it.fileKey) }
        hairClassifyFileRepository.deleteByFileKeys(deleteFilePos.mapNotNull { it.fileKey })
        //插入新增的
        val saveFilePos = dto.files.filter { !filePos.mapNotNull { it.fileKey }.contains(it.fileKey) }.map {
            MapperUtil.map(HairClassifyFilePo::class.java, it).apply {
                this.uid = UUIDUtil.createUUID()
                this.classifyUid = dto.classifyUid
            }
        }
        if (!saveFilePos.isNullOrEmpty()) {
            hairClassifyFileRepository.saveBatch(saveFilePos)
        }
    }

    /**
     * 查询所有分类
     */
    fun queryAllClassify(): List<HairClassifyDto> {
        val pos = hairClassifyRepository.list()
        if (pos.isNullOrEmpty()) return emptyList()
        return pos.map {
            MapperUtil.map(HairClassifyDto::class.java, it).apply {
                this.classifyUid = it.uid
            }
        }
    }

    /**
     * 查询分类详情
     */
    fun pageQueryClassifyDetail(param: PageQueryClassifyDetailParam): PageQueryHairDetailDto {
        //查询分类
        val classifyPo = hairClassifyRepository.queryByUid(param.classifyUid) ?: return PageQueryHairDetailDto()
        //查询图片列表
        val totalCount = hairClassifyFileRepository.queryTotalCountByClassifyUid(param.classifyUid)
        if (totalCount <= NumberUtils.INTEGER_ZERO) return PageQueryHairDetailDto(
            param.page, param.pageSize, totalCount, emptyList(), classifyPo.uid ?: "", classifyPo.classifyName ?: "", classifyPo.classifyCoverUrl ?: ""
        )
        val fileList = hairClassifyFileRepository.queryListByClassifyUid(param)
        val pageResult = MapperUtil.mapToList(HairClassifyFileDto::class.java, fileList)
        return PageQueryHairDetailDto(
            param.page, param.pageSize, totalCount, pageResult, classifyPo.uid ?: "", classifyPo.classifyName ?: "", classifyPo.classifyCoverUrl ?: ""
        )
    }

    /**
     * 删除分类
     * 1.删除分类
     * 2.删除分类图片
     * 3.删除七牛
     */
    @Transactional(rollbackFor = [Exception::class])
    fun deleteClassify(classifyUid: String) {
        hairClassifyRepository.queryByUid(classifyUid) ?: return
        hairClassifyRepository.deleteByUid(classifyUid)
        //获取所有fileKey
        val filePos = hairClassifyFileRepository.getByClassifyUid(classifyUid)
        val fileKeys = filePos.mapNotNull { it.fileKey }
        hairClassifyFileRepository.deleteByClassifyUid(classifyUid)
        CoroutineScope(Dispatchers.IO).launch {
            //删除七牛
            qiNiuUtil.delete(fileKeys)
        }
    }

    /**
     * 添加分类
     */
    @Transactional(rollbackFor = [Exception::class])
    fun saveHairClassify(dto: SaveHairClassifyDto): String {
        //添加分类表
        val classifyUid = UUIDUtil.createUUID()
        val hairClassifyPo = MapperUtil.map(HairClassifyPo::class.java, dto).apply {
            this.uid = classifyUid
        }
        hairClassifyRepository.save(hairClassifyPo)
        //添加分类文件
        val filePos = dto.files.map {
            MapperUtil.map(HairClassifyFilePo::class.java, it).apply {
                this.uid = UUIDUtil.createUUID()
                this.classifyUid = classifyUid
            }
        }
        if (!filePos.isNullOrEmpty()) {
            hairClassifyFileRepository.saveBatch(filePos)
        }
        return classifyUid
    }

}