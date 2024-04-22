package com.edu.book.domain.hair.service

import com.edu.book.domain.hair.dto.AdminLoginDto
import com.edu.book.domain.hair.dto.HairClassifyDto
import com.edu.book.domain.hair.dto.HairClassifyFileDto
import com.edu.book.domain.hair.dto.ModifyClassifyDto
import com.edu.book.domain.hair.dto.PageQueryClassifyDetailParam
import com.edu.book.domain.hair.dto.PageQueryHairDetailDto
import com.edu.book.domain.hair.dto.QueryClassifyListParam
import com.edu.book.domain.hair.dto.SaveHairClassifyDto
import com.edu.book.domain.hair.repository.HairClassifyFileRepository
import com.edu.book.domain.hair.repository.HairClassifyRepository
import com.edu.book.domain.upload.repository.UploadFileRepository
import com.edu.book.domain.user.dto.UserTokenCacheDto
import com.edu.book.domain.user.exception.AccountNotFoundException
import com.edu.book.domain.user.exception.IllegalPasswordException
import com.edu.book.domain.user.exception.UserTokenExpiredException
import com.edu.book.domain.user.repository.BookAccountRepository
import com.edu.book.infrastructure.enums.SortTypeEnum
import com.edu.book.infrastructure.po.hair.HairClassifyFilePo
import com.edu.book.infrastructure.po.hair.HairClassifyPo
import com.edu.book.infrastructure.repositoryImpl.cache.repo.UserCacheRepo
import com.edu.book.infrastructure.util.MapperUtil
import com.edu.book.infrastructure.util.QiNiuUtil
import com.edu.book.infrastructure.util.UUIDUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.apache.commons.lang3.StringUtils
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
    private lateinit var bookAccountRepository: BookAccountRepository

    @Autowired
    private lateinit var hairClassifyRepository: HairClassifyRepository

    @Autowired
    private lateinit var qiNiuUtil: QiNiuUtil

    @Autowired
    private lateinit var uploadFileRepository: UploadFileRepository

    @Autowired
    private lateinit var userCacheRepo: UserCacheRepo

    /**
     * 鉴权
     */
    fun adminUserAuth(token: String): UserTokenCacheDto {
        //获取缓存
        val cacheDto = userCacheRepo.getAdminUserToken(token) ?: throw UserTokenExpiredException()
        return cacheDto
    }

    /**
     * 管理后台登录
     */
    fun adminLogin(dto: AdminLoginDto): String {
        //查询账户信息
        val accountPo = bookAccountRepository.findByUid(dto.username) ?: throw AccountNotFoundException(dto.username)
        //判断密码是否正确
        if (!StringUtils.equals(accountPo.password, dto.password)) throw IllegalPasswordException()
        val token = UUIDUtil.createUUID()
        userCacheRepo.setHariAdminUserToken(dto.username, token)
        return token
    }

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
    fun queryAllClassify(param: QueryClassifyListParam): List<HairClassifyDto> {
        val pos = hairClassifyRepository.list()
        if (pos.isNullOrEmpty()) return emptyList()
        if (StringUtils.equals(param.sortType, SortTypeEnum.ASC.sortType)) {
            pos.sortBy { it.sort }
        } else {
            pos.sortByDescending { it.sort }
        }
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
        //获取文件信息
        val uploadFiles = uploadFileRepository.batchQuery(fileList.mapNotNull { it.fileKey }) ?: emptyList()
        val uploadFileMap = uploadFiles.associateBy { it.fileKey!! }
        val pageResult = fileList.map {
            MapperUtil.map(HairClassifyFileDto::class.java, it).apply {
                this.fileType = uploadFileMap.get(it.fileKey)?.fileType
                this.videoCoverUrl = uploadFileMap.get(it.fileKey)?.videoCoverUrl
            }
        }
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