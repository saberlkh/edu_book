package com.edu.book.domain.book.service

import com.alibaba.fastjson.JSON
import com.edu.book.domain.area.enums.LevelTypeEnum
import com.edu.book.domain.area.repository.LevelRepository
import com.edu.book.domain.book.dto.BookAgeGroupDto
import com.edu.book.domain.book.dto.BookClassifyDto
import com.edu.book.domain.book.dto.BookDetailDto
import com.edu.book.domain.book.dto.BookDto
import com.edu.book.domain.book.dto.BorrowBookDto
import com.edu.book.domain.book.dto.CollectBookDto
import com.edu.book.domain.book.dto.PageQueryBookCollectDto
import com.edu.book.domain.book.dto.PageQueryBookDto
import com.edu.book.domain.book.dto.PageQueryBookResultDto
import com.edu.book.domain.book.dto.PageQueryBorrowBookDto
import com.edu.book.domain.book.dto.PageQueryBorrowBookResultDto
import com.edu.book.domain.book.dto.PageQueryUserBookCollectParam
import com.edu.book.domain.book.dto.ReturnBookDto
import com.edu.book.domain.book.dto.ScanBookCodeInStorageParam
import com.edu.book.domain.book.enums.AgeGroupEnum
import com.edu.book.domain.book.enums.BookClassifyEnum
import com.edu.book.domain.book.enums.BookCollectStatusEnum
import com.edu.book.domain.book.enums.BookDetailStatusEnum
import com.edu.book.domain.book.exception.BookBorrowedException
import com.edu.book.domain.book.exception.BookDetailAlreadyExistException
import com.edu.book.domain.book.exception.BookDetailNotBorrowingException
import com.edu.book.domain.book.exception.BookDetailNotExistException
import com.edu.book.domain.book.exception.BookInfoNotExistException
import com.edu.book.domain.book.exception.BookNotCollectException
import com.edu.book.domain.book.exception.GardenIllegalException
import com.edu.book.domain.book.mapper.BookEntityMapper.buildBookBorrowFlowPo
import com.edu.book.domain.book.mapper.BookEntityMapper.buildBookCollectPo
import com.edu.book.domain.book.mapper.BookEntityMapper.buildBookDetailAgeGroupPos
import com.edu.book.domain.book.mapper.BookEntityMapper.buildBookDetailClassifyPos
import com.edu.book.domain.book.mapper.BookEntityMapper.buildBookDetailDto
import com.edu.book.domain.book.mapper.BookEntityMapper.buildBookDetailPo
import com.edu.book.domain.book.mapper.BookEntityMapper.buildPageQueryBookCollectDto
import com.edu.book.domain.book.mapper.BookEntityMapper.buildPageQueryBorrowBookResultDto
import com.edu.book.domain.book.mapper.BookEntityMapper.buildReturnBookBorrowFlowPo
import com.edu.book.domain.book.mapper.BookEntityMapper.buildScanBookCodeInsertBookPo
import com.edu.book.domain.book.repository.BookBorrowFlowRepository
import com.edu.book.domain.book.repository.BookCollectFlowRepository
import com.edu.book.domain.book.repository.BookDetailAgeRepository
import com.edu.book.domain.book.repository.BookDetailClassifyRepository
import com.edu.book.domain.book.repository.BookDetailRepository
import com.edu.book.domain.book.repository.BookRepository
import com.edu.book.domain.book.repository.BookSellRepository
import com.edu.book.domain.user.exception.AccountNotFoundException
import com.edu.book.domain.user.exception.AreaInfoNotExistException
import com.edu.book.domain.user.exception.ClassNotExistException
import com.edu.book.domain.user.exception.ConcurrentCreateInteractRoomException
import com.edu.book.domain.user.exception.UserNotFoundException
import com.edu.book.domain.user.repository.BookAccountRepository
import com.edu.book.domain.user.repository.BookAccountUserRelationRepository
import com.edu.book.domain.user.repository.BookUserRepository
import com.edu.book.infrastructure.config.SystemConfig
import com.edu.book.infrastructure.constants.Constants
import com.edu.book.infrastructure.constants.RedisKeyConstant.COLLECT_BOOK_KEY
import com.edu.book.infrastructure.constants.RedisKeyConstant.SCAN_BOOK_CODE_KEY
import com.edu.book.infrastructure.po.book.BookCollectFlowPo
import com.edu.book.infrastructure.po.book.BookDetailPo
import com.edu.book.infrastructure.po.book.BookPo
import com.edu.book.infrastructure.po.book.BookSellPo
import com.edu.book.infrastructure.util.DateUtil
import com.edu.book.infrastructure.util.MapperUtil
import com.edu.book.infrastructure.util.UUIDUtil
import com.edu.book.infrastructure.util.page.Page
import java.util.*
import java.util.concurrent.TimeUnit
import javax.annotation.Resource
import org.apache.commons.lang3.BooleanUtils
import org.apache.commons.lang3.ObjectUtils
import org.apache.commons.lang3.StringUtils
import org.redisson.api.RedissonClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @Auther: liukaihua
 * @Date: 2024/3/24 23:21
 * @Description:
 */

@Service
class BookDomainService {

    @Autowired
    private lateinit var bookRepository: BookRepository

    @Autowired
    private lateinit var bookSellRepository: BookSellRepository

    @Autowired
    private lateinit var bookDetailRepository: BookDetailRepository

    @Resource
    private lateinit var redissonClient: RedissonClient

    @Autowired
    private lateinit var systemConfig: SystemConfig

    @Autowired
    private lateinit var bookDetailClassifyRepository: BookDetailClassifyRepository

    @Autowired
    private lateinit var bookDetailAgeRepository: BookDetailAgeRepository

    @Autowired
    private lateinit var levelRepository: LevelRepository

    @Autowired
    private lateinit var bookBorrowFlowRepository: BookBorrowFlowRepository

    @Autowired
    private lateinit var bookAccountRepository: BookAccountRepository

    @Autowired
    private lateinit var bookAccountUserRelationRepository: BookAccountUserRelationRepository

    @Autowired
    private lateinit var bookUserRepository: BookUserRepository

    @Autowired
    private lateinit var bookCollectFlowRepository: BookCollectFlowRepository

    /**
     * 还书
     * 1.查询图书
     * 2.查询借阅记录
     * 3.设置实际归还时间和状态
     * 4.修改书籍状态
     */
    @Transactional(rollbackFor = [Exception::class])
    fun returnBook(dto: ReturnBookDto) {
        //查询图书
        bookDetailRepository.findByBookUid(dto.bookUid) ?: throw BookDetailNotExistException()
        //查询借阅记录
        val bookBorrowFlow = bookBorrowFlowRepository.findByBookUid(dto.bookUid) ?: throw BookDetailNotBorrowingException()
        //设置借阅记录实际归还时间
        val updateBorrowFlowPo = buildReturnBookBorrowFlowPo(bookBorrowFlow)
        bookBorrowFlowRepository.updateByUid(updateBorrowFlowPo)
        //修改书籍状态
        bookDetailRepository.updateBookStatus(dto.bookUid, (dto.bookStatus ?: BookDetailStatusEnum.IN_STORAGE.status))
    }

    /**
     * 收藏图书
     */
    fun collectBook(dto: CollectBookDto) {
        val lockKey = COLLECT_BOOK_KEY + dto.bookUid + dto.userUid
        val lock = redissonClient.getLock(lockKey)
        try {
            if (!lock.tryLock(systemConfig.distributedLockWaitTime, systemConfig.distributedLockReleaseTime, TimeUnit.MILLISECONDS)) {
                throw ConcurrentCreateInteractRoomException(dto.bookUid)
            }
            //查询用户和账户信息
            val userPo = bookUserRepository.findByUserUid(dto.userUid) ?: throw UserNotFoundException(dto.userUid)
            val accountPo = bookAccountRepository.findByUid(userPo.associateAccount)
            //查询图书信息
            val bookPo = bookDetailRepository.findByBookUid(dto.bookUid) ?: throw BookDetailNotExistException()
            when(dto.collectStatus) {

                /**
                 * 收藏
                 */
                BookCollectStatusEnum.COLLECTED.status -> {
                    //查询收藏流水
                    val bookCollectFlow = bookCollectFlowRepository.findUserCollectFlowByBookUid(dto.userUid, dto.bookUid)
                    if (bookCollectFlow == null) {
                        val collectPo = buildBookCollectPo(bookPo, userPo, accountPo, dto)
                        bookCollectFlowRepository.save(collectPo)
                    } else {
                        bookCollectFlow.collectStatus = BooleanUtils.toBoolean(BookCollectStatusEnum.COLLECTED.status)
                        bookCollectFlowRepository.updateBookCollectFlow(bookCollectFlow)
                    }
                }

                /**
                 * 取消收藏
                 */
                BookCollectStatusEnum.UN_COLLECTED.status -> {
                    //查询收藏流水
                    val bookCollectFlow = bookCollectFlowRepository.findUserCollectFlowByBookUid(dto.userUid, dto.bookUid) ?: throw BookNotCollectException()
                    val updatedBookCollectFlow = BookCollectFlowPo().apply {
                        this.uid = bookCollectFlow.uid
                        this.collectStatus = BooleanUtils.toBoolean(BookCollectStatusEnum.UN_COLLECTED.status)
                    }
                    bookCollectFlowRepository.updateBookCollectFlow(updatedBookCollectFlow)
                }

            }
        } finally {
            if (lock.isHeldByCurrentThread) {
                lock.unlock()
            }
        }
    }

    /**
     * 分页查询收藏列表
     */
    fun pageQueryCollectList(param: PageQueryUserBookCollectParam): Page<PageQueryBookCollectDto> {
        //获取用户信息
        val userPo = bookUserRepository.findByUserUid(param.userUid) ?: throw UserNotFoundException(param.userUid)
        val pageQuery = bookCollectFlowRepository.pageQueryBookCollect(param)
        if (pageQuery.records.isNullOrEmpty()) return Page()
        //查询图书信息
        val bookUids = pageQuery.records.mapNotNull { it.bookUid }
        val bookDetailPos = bookDetailRepository.findByBookUids(bookUids) ?: emptyList()
        val bookDetailPoMap = bookDetailPos.associateBy { it.bookUid!! }
        val result = pageQuery.records.mapNotNull {
            val bookDetailPo = bookDetailPoMap.get(it.bookUid)
            buildPageQueryBookCollectDto(it, bookDetailPo, userPo)
        }
        return Page(param.page, param.pageSize, pageQuery.total.toInt(), result)
    }

    /**
     * 分页查询借阅列表
     */
    fun pageQueryBorrowFlow(dto: PageQueryBorrowBookDto): Page<PageQueryBorrowBookResultDto> {
        val pageQuery = bookBorrowFlowRepository.pageQueryBorrowFlow(dto)
        if (pageQuery.records.isNullOrEmpty()) return Page()
        //查询图书详情信息
        val bookUids = pageQuery.records.mapNotNull { it.bookUid }
        val bookDetailPos = bookDetailRepository.findByBookUids(bookUids) ?: emptyList()
        val bookDetailPoMap = bookDetailPos.associateBy { it.bookUid!! }
        val result = pageQuery.records.mapNotNull {
            val bookDetailPo = bookDetailPoMap.get(it.bookUid)
            buildPageQueryBorrowBookResultDto(it, bookDetailPo)
        }
        return Page(dto.page, dto.pageSize, pageQuery.total.toInt(), result)
    }

    /**
     * 借书
     * 1.查询账户
     * 2.查询书籍
     * 3.添加书籍借阅流水
     * 4.更新书籍状态
     */
    @Transactional(rollbackFor = [Exception::class])
    fun borrowBook(dto: BorrowBookDto) {
        //查询账户信息
        val accountInfo = bookAccountRepository.findByBorrwoCardId(dto.borrowCardId) ?: throw AccountNotFoundException(dto.borrowCardId)
        //查询用户信息
        val accountUserRelationPo = bookAccountUserRelationRepository.findByAccountUid(accountInfo.accountUid) ?: throw AccountNotFoundException(dto.borrowCardId)
        val userInfoPo = bookUserRepository.findByUserUid(accountUserRelationPo.userUid!!) ?: throw UserNotFoundException(accountUserRelationPo.userUid!!)
        //查询书籍信息
        val bookDetailInfo = bookDetailRepository.findByBookUid(dto.bookUid) ?: throw BookDetailNotExistException()
        if (ObjectUtils.equals(bookDetailInfo.status, BookDetailStatusEnum.BORROWED.status)) throw BookBorrowedException()
        //查看班级、幼儿园信息
        val classInfo = levelRepository.queryByUid(accountInfo.classUid!!, LevelTypeEnum.Classroom) ?: throw ClassNotExistException()
        //查询年级
        val gradeInfo = levelRepository.queryByUid(classInfo.parentUid!!, LevelTypeEnum.Grade) ?: throw ClassNotExistException()
        //查询园区
        val gardenInfo = levelRepository.queryByUid(gradeInfo.parentUid!!, LevelTypeEnum.Garden) ?: throw ClassNotExistException()
        //判断账号的园区和书籍的园区是否对应的上
        if (!StringUtils.equals(gardenInfo.uid, bookDetailInfo.gardenUid)) throw GardenIllegalException()
        //查询书籍信息
        val bookInfo = bookRepository.findByIsbnCode(bookDetailInfo.isbnCode) ?: throw BookInfoNotExistException()
        //添加书籍借阅流水
        val borrowFlowPo = buildBookBorrowFlowPo(bookInfo, bookDetailInfo, userInfoPo, dto, accountInfo, gardenInfo)
        bookBorrowFlowRepository.save(borrowFlowPo)
        //更新书籍状态
        val updateBookDetailPo = BookDetailPo().apply {
            this.status = BookDetailStatusEnum.BORROWED.status
            this.outStorageTime = Date()
        }
        bookDetailRepository.updateByBookUid(updateBookDetailPo, dto.bookUid)
    }

    /**
     * 根据isbn查询书
     */
    fun findBookByIsbnCode(isbnCode: String): BookDto? {
        val po = bookRepository.findByIsbnCode(isbnCode) ?: return null
        return MapperUtil.map(BookDto::class.java, po)
    }

    /**
     * 删除图书详情
     * 1.删除bookdetail
     * 2.删除分类
     * 3.删除年龄段
     */
    @Transactional(rollbackFor = [Exception::class])
    fun deleteBookDetail(bookUid: String) {
        bookDetailRepository.findByBookUid(bookUid) ?: return
        bookDetailRepository.deleteByBookUid(bookUid)
        bookDetailClassifyRepository.deleteByBookUid(bookUid)
        bookDetailAgeRepository.deleteByBookUid(bookUid)
        //删除借阅记录
        bookBorrowFlowRepository.deleteByBookUid(bookUid)
        //删除收藏记录
        bookCollectFlowRepository.deleteByBookUid(bookUid)
    }

    /**
     * 查询图书详情
     */
    fun findBookDetail(bookUid: String, userUid: String?): BookDetailDto {
        //查询图书详情信息
        val detailPo = bookDetailRepository.findByBookUid(bookUid) ?: throw BookDetailNotExistException()
        //查询isbn信息
        val bookPo = bookRepository.findByIsbnCode(detailPo.isbnCode) ?: throw BookInfoNotExistException()
        //查询分类信息
        val classifyList = bookDetailClassifyRepository.findClassifyList(bookUid, detailPo.isbnCode!!)
        //查询年龄段
        val ageGroups = bookDetailAgeRepository.findByBookUid(detailPo.isbnCode!!, bookUid)
        //查询收藏状态
        val collectFlowPo = if (userUid.isNullOrBlank()) {
            null
        } else {
            bookCollectFlowRepository.findUserCollectFlowByBookUid(userUid, bookUid)
        }
        //参数组装
        return buildBookDetailDto(detailPo, classifyList, ageGroups, collectFlowPo)
    }

    /**
     * 图书扫码入库
     * 1.判断isbn是否正确
     * 2.新增书籍
     * 3.修改图书基础信息
     */
    @Transactional(rollbackFor = [Exception::class])
    fun scanBookCodeInStorage(dto: ScanBookCodeInStorageParam) {
        val lockKey = SCAN_BOOK_CODE_KEY + dto.bookUid
        val lock = redissonClient.getLock(lockKey)
        try {
            if (!lock.tryLock(systemConfig.distributedLockWaitTime, systemConfig.distributedLockReleaseTime, TimeUnit.MILLISECONDS)) {
                throw ConcurrentCreateInteractRoomException(dto.bookUid)
            }
            //查询园区信息
            val gardenInfo = levelRepository.queryByUid(dto.gardenUid, LevelTypeEnum.Garden) ?: throw AreaInfoNotExistException()
            //查询图书是否已经存在
            val currentBookDetailPo = bookDetailRepository.findByBookUid(dto.bookUid)
            if (currentBookDetailPo != null) throw BookDetailAlreadyExistException()
            //根据isbn查询图书信息
            val bookPo = bookRepository.findByIsbnCode(dto.isbn)
            if (bookPo == null) {
                //新增po
                val insertBookPo = buildScanBookCodeInsertBookPo(dto)
                bookRepository.save(insertBookPo)
            }
            //新增图书详情
            val bookDetailPo = buildBookDetailPo(dto, gardenInfo)
            bookDetailRepository.save(bookDetailPo)
            //添加分类
            val classifyPos = buildBookDetailClassifyPos(dto)
            bookDetailClassifyRepository.saveBatch(classifyPos)
            //添加年龄段
            val ageGroups = buildBookDetailAgeGroupPos(dto)
            bookDetailAgeRepository.saveBatch(ageGroups)
        } finally {
            if (lock.isHeldByCurrentThread) {
                lock.unlock()
            }
        }
    }

    /**
     * 插入数据
     */
    fun saveIsbnBookInfo(dto: BookDto) {
        val bookPo = MapperUtil.map(BookPo::class.java, dto).apply {
            this.uid = UUIDUtil.createUUID()
            this.summary = JSON.toJSONString(dto.summary)
        }
        bookRepository.save(bookPo)
        val bookSellList = MapperUtil.mapToList(BookSellPo::class.java, dto.sellList).map {
            it.uid = UUIDUtil.createUUID()
            it.isbnCode = dto.isbnCode
            it
        }
        bookSellRepository.saveBatch(bookSellList)
    }

    /**
     * 分页查询
     */
    fun pageQueryBooks(dto: PageQueryBookDto): Page<PageQueryBookResultDto> {
        val pageQuery = bookRepository.pageQueryBooks(dto)
        if (pageQuery.records.isNullOrEmpty()) return Page()
        val bookUids = pageQuery.records.mapNotNull { it.bookUid }
        //查询分类和年龄段
        val classifyPos = bookDetailClassifyRepository.batchQueryClassifyList(bookUids)
        val classifyPoMap = classifyPos.groupBy { it.bookUid!! }
        val ageGroups = bookDetailAgeRepository.batchQueryBookAgeGroups(bookUids)
        val ageGroupMap = ageGroups.groupBy { it.bookUid!! }
        //参数组装
        val bookDtos = pageQuery.records.map { entity ->
            val result = MapperUtil.map(PageQueryBookResultDto::class.java, entity, excludes = listOf("price", "page", "pubdate", "ageGroups", "classify")).apply {
                this.price = entity.price?.toDouble()?.div(Constants.hundred)?.toString()
                this.page = entity.page?.toString()
                this.pubdate = if (entity.pubdate != null) DateUtil.format(entity.pubdate!!, DateUtil.PATTREN_DATE3) else ""
                val bookClassifyPos = classifyPoMap.get(entity.bookUid)?.mapNotNull { it.classify }
                this.classifyList = bookClassifyPos?.mapNotNull {
                    BookClassifyDto.buildBookClassifyDto(BookClassifyEnum.getDescByCode(it), it)
                } ?: emptyList()
                val bookAgeGroups = ageGroupMap.get(entity.bookUid)?.mapNotNull { it.ageGroup }
                this.ageGroups = bookAgeGroups?.mapNotNull {
                    BookAgeGroupDto.buildBookAgeGroupDto(AgeGroupEnum.getDescByCode(it), it)
                } ?: emptyList()
            }
            result
        }
        return Page(dto.page, dto.pageSize, pageQuery.total.toInt(), bookDtos)
    }

    /**
     * 查询isbn列表
     */
    fun getIsbnList(garden: String?, isbn: String?): List<String> {
        return bookRepository.findIsbnList(garden, isbn).mapNotNull { it.isbnCode }
    }

}