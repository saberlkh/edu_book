package com.edu.book.domain.book.service

import com.alibaba.fastjson.JSON
import com.edu.book.domain.area.enums.LevelTypeEnum
import com.edu.book.domain.area.repository.LevelRepository
import com.edu.book.domain.book.dto.AddBookMenuDto
import com.edu.book.domain.book.dto.BookAgeGroupDto
import com.edu.book.domain.book.dto.BookClassifyDto
import com.edu.book.domain.book.dto.BookDetailDto
import com.edu.book.domain.book.dto.BookDto
import com.edu.book.domain.book.dto.BookMenuIsbnResultDto
import com.edu.book.domain.book.dto.BorrowBookDto
import com.edu.book.domain.book.dto.CancelReservationBookDto
import com.edu.book.domain.book.dto.ChoicenessPageQueryDto
import com.edu.book.domain.book.dto.CollectBookDto
import com.edu.book.domain.book.dto.ModifyBookDetailDto
import com.edu.book.domain.book.dto.ModifyBookGardenDto
import com.edu.book.domain.book.dto.ModifyBookMenuDto
import com.edu.book.domain.book.dto.PageQueryBookCollectDto
import com.edu.book.domain.book.dto.PageQueryBookDto
import com.edu.book.domain.book.dto.PageQueryBookIsbnDto
import com.edu.book.domain.book.dto.PageQueryBookIsbnResultEntity
import com.edu.book.domain.book.dto.PageQueryBookResultDto
import com.edu.book.domain.book.dto.PageQueryBorrowBookDto
import com.edu.book.domain.book.dto.PageQueryBorrowBookResultDto
import com.edu.book.domain.book.dto.PageQueryUserBookCollectParam
import com.edu.book.domain.book.dto.QueryBookMenuResultDto
import com.edu.book.domain.book.dto.ReservationBookDto
import com.edu.book.domain.book.dto.ReservationBookPageQueryDto
import com.edu.book.domain.book.dto.ReservationBookPageResultDto
import com.edu.book.domain.book.dto.ReservationUserDto
import com.edu.book.domain.book.dto.ReturnBookDto
import com.edu.book.domain.book.dto.ScanBookCodeInStorageParam
import com.edu.book.domain.book.enums.AgeGroupEnum
import com.edu.book.domain.book.enums.BookClassifyEnum
import com.edu.book.domain.book.enums.BookCollectStatusEnum
import com.edu.book.domain.book.enums.BookDetailStatusEnum
import com.edu.book.domain.book.enums.ReservationStatusEnum
import com.edu.book.domain.book.enums.SortByColumnEnum
import com.edu.book.domain.book.exception.BookBorrowedException
import com.edu.book.domain.book.exception.BookDetailAlreadyExistException
import com.edu.book.domain.book.exception.BookDetailNotBorrowingException
import com.edu.book.domain.book.exception.BookDetailNotExistException
import com.edu.book.domain.book.exception.BookInfoNotExistException
import com.edu.book.domain.book.exception.BookMenuNotExistException
import com.edu.book.domain.book.exception.BookNotCollectException
import com.edu.book.domain.book.exception.BookReservationException
import com.edu.book.domain.book.exception.BookStorageNotEnoughException
import com.edu.book.domain.book.exception.GardenIllegalException
import com.edu.book.domain.book.mapper.BookEntityMapper.buildBookBorrowFlowPo
import com.edu.book.domain.book.mapper.BookEntityMapper.buildBookCollectPo
import com.edu.book.domain.book.mapper.BookEntityMapper.buildBookDetailAgeGroupPos
import com.edu.book.domain.book.mapper.BookEntityMapper.buildBookDetailClassifyPos
import com.edu.book.domain.book.mapper.BookEntityMapper.buildBookDetailDto
import com.edu.book.domain.book.mapper.BookEntityMapper.buildBookDetailPo
import com.edu.book.domain.book.mapper.BookEntityMapper.buildModifyBookDetailPo
import com.edu.book.domain.book.mapper.BookEntityMapper.buildPageQueryBookCollectDto
import com.edu.book.domain.book.mapper.BookEntityMapper.buildPageQueryBorrowBookResultDto
import com.edu.book.domain.book.mapper.BookEntityMapper.buildReturnBookBorrowFlowPo
import com.edu.book.domain.book.mapper.BookEntityMapper.buildScanBookCodeInsertBookPo
import com.edu.book.domain.book.repository.BookBorrowFlowRepository
import com.edu.book.domain.book.repository.BookCollectFlowRepository
import com.edu.book.domain.book.repository.BookDetailAgeRepository
import com.edu.book.domain.book.repository.BookDetailClassifyRepository
import com.edu.book.domain.book.repository.BookDetailRepository
import com.edu.book.domain.book.repository.BookMenuRelationRepository
import com.edu.book.domain.book.repository.BookMenuRepository
import com.edu.book.domain.book.repository.BookRepository
import com.edu.book.domain.book.repository.BookReservationFlowRepository
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
import com.edu.book.infrastructure.constants.RedisKeyConstant.BORROW_BOOK_LOCK_KEY
import com.edu.book.infrastructure.constants.RedisKeyConstant.COLLECT_BOOK_KEY
import com.edu.book.infrastructure.constants.RedisKeyConstant.MODIFY_BOOK_DETAIL_KEY
import com.edu.book.infrastructure.constants.RedisKeyConstant.RESERVATION_BOOK_LOCK_KEY
import com.edu.book.infrastructure.constants.RedisKeyConstant.SCAN_BOOK_CODE_KEY
import com.edu.book.infrastructure.po.book.BookCollectFlowPo
import com.edu.book.infrastructure.po.book.BookDetailPo
import com.edu.book.infrastructure.po.book.BookMenuPo
import com.edu.book.infrastructure.po.book.BookMenuRelationPo
import com.edu.book.infrastructure.po.book.BookPo
import com.edu.book.infrastructure.po.book.BookReservationFlowPo
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
import org.apache.commons.lang3.math.NumberUtils
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

    @Autowired
    private lateinit var bookMenuRepository: BookMenuRepository

    @Autowired
    private lateinit var bookMenuRelationRepository: BookMenuRelationRepository

    @Autowired
    private lateinit var bookReservationFlowRepository: BookReservationFlowRepository

    /**
     * 编辑书单
     */
    @Transactional(rollbackFor = [Exception::class])
    fun modifyBookMenu(dto: ModifyBookMenuDto) {
        //查询书单信息
        val bookMenuPo = bookMenuRepository.getByUid(dto.bookMenuUid!!) ?: throw BookMenuNotExistException()
        //更新书单信息
        val modifyBookMenuPo = MapperUtil.map(BookMenuPo::class.java, dto).apply {
            this.uid = dto.bookMenuUid
        }
        bookMenuRepository.modifyByUid(modifyBookMenuPo)
        //更新书单关联信息
        bookMenuRelationRepository.deleteByMenuUid(bookMenuPo.uid!!)
        val newBookMenuRelationPos = dto.isbns.map {
            BookMenuRelationPo().apply {
                this.uid = UUIDUtil.createUUID()
                this.bookMenuUid = dto.bookMenuUid
                this.isbn = it
            }
        }
        bookMenuRelationRepository.saveBatch(newBookMenuRelationPos)
    }

    /**
     * 删除书单
     */
    @Transactional(rollbackFor = [Exception::class])
    fun deleteBookMenu(bookMenuUid: String) {
        bookMenuRepository.removeByUid(bookMenuUid)
        bookMenuRelationRepository.removeByBookMenuUid(bookMenuUid)
    }

    /**
     * 添加书单
     */
    @Transactional(rollbackFor = [Exception::class])
    fun addBookMenu(dto: AddBookMenuDto) {
        //判断是否已经加入了书单
        val bookMenuUid = UUIDUtil.createUUID()
        val bookMenuPo = MapperUtil.map(BookMenuPo::class.java, dto).apply {
            this.uid = bookMenuUid
        }
        bookMenuRepository.save(bookMenuPo)
        //添加书单关联数据
        val bookMenuRelationPos = dto.isbns.map {
            BookMenuRelationPo().apply {
                this.uid = UUIDUtil.createUUID()
                this.bookMenuUid = bookMenuUid
                this.isbn = it
            }
        }
        bookMenuRelationRepository.saveBatch(bookMenuRelationPos)
    }

    /**
     * 查询书单列表
     */
    fun getBookMenus(gardenUid: String?, kindergartenUid: String?): List<QueryBookMenuResultDto> {
        //获取书单列表
        val bookMenuPos = bookMenuRepository.getByGardenUid(gardenUid, kindergartenUid) ?: return emptyList()
        //查询园区信息
        val gardenInfos = levelRepository.batchQueryByUids(bookMenuPos.mapNotNull { it.gardenUid }, LevelTypeEnum.Garden) ?: emptyList()
        val gardenInfoMap = gardenInfos.associateBy { it.uid!! }
        //查询幼儿园信息
        val kindergartenInfos = levelRepository.batchQueryByUids(bookMenuPos.mapNotNull { it.kindergartenUid }, LevelTypeEnum.Kindergarten) ?: emptyList()
        val kindergartenInfoMap = kindergartenInfos.associateBy { it.uid!! }
        //获取书单对应的isbn
        val bookMenuIsbnPos = bookMenuRelationRepository.getByMenuUids(bookMenuPos.mapNotNull { it.uid }) ?: emptyList()
        val bookMenuIsbnMap = bookMenuIsbnPos.groupBy { it.bookMenuUid!! }
        //查询isbn信息
        val bookInfoMap = bookRepository.findByIsbnCodes(bookMenuIsbnPos.mapNotNull { it.isbn!! }.distinct())?.associateBy { it.isbnCode!! } ?: emptyMap()
        //返回参数
        val result = bookMenuPos.map {
            val bookMenuIsbns = bookMenuIsbnMap.get(it.uid) ?: emptyList()
            val gardenInfo = gardenInfoMap.get(it.gardenUid)
            val kindergartenInfo = kindergartenInfoMap.get(it.kindergartenUid)
            QueryBookMenuResultDto().apply {
                this.bookMenuUid = it.uid!!
                this.menuPic = it.menuPic ?: ""
                this.menuTitle = it.menuTitle ?: ""
                this.menuDesc = it.menuDesc ?: ""
                this.garden = gardenInfo?.levelName ?: ""
                this.gardenUid = it.gardenUid ?: ""
                this.kindergartenName = kindergartenInfo?.levelName ?: ""
                this.kindergartenUid = kindergartenInfo?.uid ?: ""
                this.books = bookMenuIsbns.map {
                    val bookInfo = bookInfoMap.get(it.isbn)
                    BookMenuIsbnResultDto().apply {
                        this.title = bookInfo?.title ?: ""
                        this.subtitle = bookInfo?.subTitle ?: ""
                        this.pic = bookInfo?.picUrl ?: ""
                        this.author = bookInfo?.author ?: ""
                        this.summary = bookInfo?.summary ?: ""
                        this.publisher = bookInfo?.publisher ?: ""
                        this.isbn = bookInfo?.isbnCode!!
                    }
                }
            }
        }
        return result
    }

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
            val bookDetailPo = bookDetailRepository.findByBookUid(dto.bookUid) ?: throw BookDetailNotExistException()
            when(dto.collectStatus) {

                /**
                 * 收藏
                 */
                BookCollectStatusEnum.COLLECTED.status -> {
                    //查询收藏流水
                    val bookCollectFlow = bookCollectFlowRepository.findUserCollectFlowByBookUid(dto.userUid, dto.bookUid)
                    if (bookCollectFlow == null) {
                        val collectPo = buildBookCollectPo(bookDetailPo, userPo, accountPo, dto)
                        bookCollectFlowRepository.save(collectPo)
                    } else {
                        bookCollectFlow.collectStatus = BooleanUtils.toBoolean(BookCollectStatusEnum.COLLECTED.status)
                        bookCollectFlowRepository.updateBookCollectFlow(bookCollectFlow)
                    }
                    //收藏数 + 1
                    val finalCollectCount = (bookDetailPo.collectCount ?: NumberUtils.INTEGER_ZERO) + NumberUtils.INTEGER_ONE
                    bookDetailRepository.modifyBookCollectCount(bookDetailPo.bookUid!!, finalCollectCount)
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
                    //收藏数 - 1
                    val bookCollectCount = (bookDetailPo.collectCount ?: NumberUtils.INTEGER_ZERO) - NumberUtils.INTEGER_ONE
                    val finalCollectCount = if (bookCollectCount <= NumberUtils.INTEGER_ZERO) {
                        NumberUtils.INTEGER_ZERO
                    } else {
                        bookCollectCount
                    }
                    bookDetailRepository.modifyBookCollectCount(bookDetailPo.bookUid!!, finalCollectCount)
                }

            }
        } finally {
            if (lock.isHeldByCurrentThread) {
                lock.unlock()
            }
        }
    }

    /**
     * 修改图书所属园区
     */
    fun modifyBookGarden(dto: ModifyBookGardenDto) {
        //查询园区信息
        val gardenInfo = levelRepository.queryByUid(dto.gardenUid, LevelTypeEnum.Garden) ?: throw ClassNotExistException()
        //修改图书信息
        bookDetailRepository.batchModifyBookDetailGarden(dto.bookUids, dto.gardenUid)
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
        //查询用户信息
        val userUids = pageQuery.records.mapNotNull { it.borrowUserUid }
        val userInfoMap = bookUserRepository.batchQueryByUserUids(userUids)?.associateBy { it.uid!! } ?: emptyMap()
        //查询账户信息
        val accountUids = userInfoMap.mapNotNull { it.value.associateAccount }
        val accountInfoMap = bookAccountRepository.batchQueryByAccountUids(accountUids)?.associateBy { it.accountUid!! } ?: emptyMap()
        //查询园区
        val gardenUids = pageQuery.records.mapNotNull { it.gardenUid }
        val gardenInfos = levelRepository.batchQueryByUids(gardenUids, LevelTypeEnum.Garden) ?: throw ClassNotExistException()
        val gardenInfoMap = gardenInfos.associateBy { it.uid!! }
        val result = pageQuery.records.mapNotNull {
            val bookDetailPo = bookDetailPoMap.get(it.bookUid)
            buildPageQueryBorrowBookResultDto(it, bookDetailPo, userInfoMap, accountInfoMap, gardenInfoMap)
        }
        return Page(dto.page, dto.pageSize, pageQuery.total.toInt(), result)
    }

    /**
     * 查询书单预订列表
     */
    fun getReservationBookPage(dto: ReservationBookPageQueryDto): Page<ReservationBookPageResultDto> {
        val pageQuery = bookReservationFlowRepository.pageQueryReservation(dto)
        if (pageQuery.records.isNullOrEmpty()) return Page()
        val pageQueryResult = pageQuery.records
        //查询图书信息
        val bookInfoMap = bookRepository.findByIsbnCodes(pageQueryResult.mapNotNull { it.isbn })?.associateBy { it.isbnCode!! } ?: emptyMap()
        //查询用户信息
        val userInfoMap = bookUserRepository.batchQueryByUserUids(pageQueryResult.mapNotNull { it.reservationUserUid })?.associateBy { it.uid!! } ?: emptyMap()
        //查询账户信息
        val accountInfos = bookAccountRepository.batchQueryByAccountUids(userInfoMap.mapNotNull { it.value.associateAccount }) ?: emptyList()
        val accountInfoMap = accountInfos.associateBy { it.accountUid!! }
        //查询园区信息
        //查看班级、幼儿园信息
        val classInfos = levelRepository.batchQueryByUids(accountInfos.mapNotNull { it.classUid!! }, LevelTypeEnum.Classroom) ?: throw ClassNotExistException()
        //查询年级
        val gradeInfos = levelRepository.batchQueryByUids(classInfos.mapNotNull { it.parentUid!! }, LevelTypeEnum.Grade) ?: throw ClassNotExistException()
        //查询园区
        val gardenInfos = levelRepository.batchQueryByUids(gradeInfos.mapNotNull { it.parentUid }, LevelTypeEnum.Garden) ?: throw ClassNotExistException()
        val gardenInfoMap = gardenInfos.associateBy { it.uid!! }
        //查询幼儿园
        val kindergartenInfos = levelRepository.batchQueryByUids(gardenInfos.mapNotNull { it.parentUid }, LevelTypeEnum.Kindergarten) ?: throw ClassNotExistException()
        val kindergartenInfoMap = kindergartenInfos.associateBy { it.uid!! }
        //组装参数
        val result = pageQuery.records.mapNotNull {
            val bookInfo = bookInfoMap.get(it.isbn)
            val userInfo = userInfoMap.get(it.reservationUserUid)
            val gardenInfo = gardenInfoMap.get(it.gardenUid)
            val accountInfo = accountInfoMap.get(userInfo?.associateAccount)
            ReservationBookPageResultDto().apply {
                this.isbn = it.isbn!!
                this.title = bookInfo?.title ?: ""
                this.subtitle = bookInfo?.subTitle ?: ""
                this.summary = bookInfo?.summary ?: ""
                this.pic = bookInfo?.picUrl ?: ""
                this.reservationUser = ReservationUserDto().apply {
                    this.userUid = userInfo?.uid ?: ""
                    this.nickname = accountInfo?.accountNickName ?: ""
                    this.gardenUid = it.gardenUid ?: ""
                    this.gardenName = gardenInfo?.levelName ?: ""
                }
            }
        }
        return Page(dto.page, dto.pageSize, pageQuery.total.toInt(), result)
    }

    /**
     * 取消预定
     */
    fun cancelReservationBook(dto: CancelReservationBookDto) {
        val lockKey = RESERVATION_BOOK_LOCK_KEY + dto.isbn
        val lock = redissonClient.getLock(lockKey)
        try {
            if (!lock.tryLock(systemConfig.distributedLockWaitTime, systemConfig.distributedLockReleaseTime, TimeUnit.MILLISECONDS)) {
                throw ConcurrentCreateInteractRoomException(dto.isbn)
            }
            //查询图书信息
            val bookInfo = bookRepository.findByIsbnCode(dto.isbn) ?: throw BookInfoNotExistException()
            //删除图书预订流水记录
            bookReservationFlowRepository.deleteUserReservation(dto.userUid, dto.isbn)
            //更新书本库存
            val modifyBookPo = BookPo().apply {
                this.uid = bookInfo.uid
                this.bookStorage = (bookInfo.bookStorage ?: NumberUtils.INTEGER_ZERO) + NumberUtils.INTEGER_ONE
            }
            bookRepository.updateByUid(modifyBookPo)
        } finally {
            if (lock.isHeldByCurrentThread) lock.unlock()
        }
    }

    /**
     * 书本预订
     * 1.查询isbn信息
     * 2.判断库存
     * 3.扣减库存
     * 4.新增预定记录
     */
    @Transactional(rollbackFor = [Exception::class])
    fun reservationBook(dto: ReservationBookDto) {
        val lockKey = RESERVATION_BOOK_LOCK_KEY + dto.isbn
        val lock = redissonClient.getLock(lockKey)
        try {
            if (!lock.tryLock(systemConfig.distributedLockWaitTime, systemConfig.distributedLockReleaseTime, TimeUnit.MILLISECONDS)) {
                throw ConcurrentCreateInteractRoomException(dto.isbn)
            }
            //判断图书是否有预定中
            val currentBookReservationFlow = bookReservationFlowRepository.queryUserReservationByIsbn(dto.userUid, dto.isbn)
            if (currentBookReservationFlow != null) throw BookReservationException()
            //查询账户信息
            val accountInfo = bookAccountRepository.findByBorrwoCardId(dto.borrowCardId) ?: throw AccountNotFoundException(dto.borrowCardId)
            //查看班级、幼儿园信息
            val classInfo = levelRepository.queryByUid(accountInfo.classUid!!, LevelTypeEnum.Classroom) ?: throw ClassNotExistException()
            //查询年级
            val gradeInfo = levelRepository.queryByUid(classInfo.parentUid!!, LevelTypeEnum.Grade) ?: throw ClassNotExistException()
            //查询园区
            val gardenInfo = levelRepository.queryByUid(gradeInfo.parentUid!!, LevelTypeEnum.Garden) ?: throw ClassNotExistException()
            val kindergartenInfo = levelRepository.queryByUid(gardenInfo.parentUid!!, LevelTypeEnum.Kindergarten) ?: throw ClassNotExistException()
            val bookInfo = bookRepository.findByIsbnCode(dto.isbn) ?: throw BookInfoNotExistException()
            if ((bookInfo.bookStorage ?: NumberUtils.INTEGER_ZERO) <= NumberUtils.INTEGER_ZERO ) throw BookStorageNotEnoughException()
            //更新书本库存
            val modifyBookPo = BookPo().apply {
                this.uid = bookInfo.uid
                this.bookStorage = bookInfo.bookStorage!! - NumberUtils.INTEGER_ONE
            }
            bookRepository.updateByUid(modifyBookPo)
            //新增预定记录
            val bookReservationFlowPo = BookReservationFlowPo().apply {
                this.uid = UUIDUtil.createUUID()
                this.reservationUserUid = dto.userUid
                this.isbn = dto.isbn
                this.reservationStatus = ReservationStatusEnum.Reservationing.status
                this.gardenUid = gardenInfo.uid
                this.kindergartenUid = kindergartenInfo.uid
            }
            bookReservationFlowRepository.save(bookReservationFlowPo)
        } finally {
            if (lock.isHeldByCurrentThread) lock.unlock()
        }
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
        val lockKey = BORROW_BOOK_LOCK_KEY + dto.bookUid
        val lock = redissonClient.getLock(lockKey)
        try {
            if (!lock.tryLock(systemConfig.distributedLockWaitTime, systemConfig.distributedLockReleaseTime, TimeUnit.MILLISECONDS)) {
                throw ConcurrentCreateInteractRoomException(dto.bookUid)
            }
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
            //更新图书预订状态
            val bookReservationPo = bookReservationFlowRepository.queryUserReservationByIsbn(userInfoPo.uid!!, bookInfo.isbnCode!!)
            if (bookReservationPo != null) {
                bookReservationFlowRepository.modifyStatusByUid(bookReservationPo.uid!!, ReservationStatusEnum.Borrowed.status)
            }
        } finally {
            if (lock.isHeldByCurrentThread) lock.unlock()
        }
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
     * 编辑图书
     */
    @Transactional(rollbackFor = [Exception::class])
    fun modifyBookDetail(dto: ModifyBookDetailDto) {
        val lockKey = MODIFY_BOOK_DETAIL_KEY + dto.bookUid
        val lock = redissonClient.getLock(lockKey)
        try {
            if (!lock.tryLock(systemConfig.distributedLockWaitTime, systemConfig.distributedLockReleaseTime, TimeUnit.MILLISECONDS)) {
                throw ConcurrentCreateInteractRoomException(dto.bookUid)
            }
            //查询图书详情
            val bookDetailPo = bookDetailRepository.findByBookUid(dto.bookUid) ?: throw BookDetailNotExistException()
            //查询园区信息
            val gardenInfo = levelRepository.queryByUid(bookDetailPo.gardenUid!!, LevelTypeEnum.Garden) ?: throw AreaInfoNotExistException()
            //更新图书详情信息
            val modifyBookDetailPo = buildModifyBookDetailPo(dto, gardenInfo)
            bookDetailRepository.updateByBookUid(modifyBookDetailPo, dto.bookUid)
            //更新分类和年龄段
            bookDetailClassifyRepository.deleteByBookUid(dto.bookUid)
            bookDetailAgeRepository.deleteByBookUid(dto.bookUid)
            //添加分类
            val classifyPos = buildBookDetailClassifyPos(dto)
            bookDetailClassifyRepository.saveBatch(classifyPos)
            //添加年龄段
            val ageGroups = buildBookDetailAgeGroupPos(dto)
            bookDetailAgeRepository.saveBatch(ageGroups)
        } finally {
            if (lock.isHeldByCurrentThread) lock.unlock()
        }
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
            } else {
                //修改图书库存
                bookPo.bookStorage = (bookPo.bookStorage ?: NumberUtils.INTEGER_ZERO) + NumberUtils.INTEGER_ONE
                bookRepository.updateByUid(bookPo)
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
     * 获取图书精选
     */
    fun getChoicenessPage(dto: ChoicenessPageQueryDto): Page<PageQueryBookResultDto> {
        val pageQuery = bookDetailRepository.pageQueryBookChoiceness(dto)
        if (pageQuery.records.isNullOrEmpty()) return Page()
        val bookUids = pageQuery.records.mapNotNull { it.bookUid }
        //查询分类和年龄段
        val classifyPos = bookDetailClassifyRepository.batchQueryClassifyList(bookUids)
        val classifyPoMap = classifyPos.groupBy { it.bookUid!! }
        val ageGroups = bookDetailAgeRepository.batchQueryBookAgeGroups(bookUids)
        val ageGroupMap = ageGroups.groupBy { it.bookUid!! }
        //查询园区信息
        val gardenInfoMap = levelRepository.batchQueryByUids(pageQuery.records.mapNotNull { it.gardenUid!! }, LevelTypeEnum.Garden)?.associateBy { it.uid } ?: emptyMap()
        //参数组装
        val result = pageQuery.records.mapNotNull { po ->
            val gardenInfo = gardenInfoMap.get(po.gardenUid)
            val classifyPoList = classifyPoMap.get(po.bookUid) ?: emptyList()
            val ageGroupPos = ageGroupMap.get(po.bookUid) ?: emptyList()
            PageQueryBookResultDto().apply {
                this.title = po.title
                this.subtitle = po.subTitle
                this.pic = po.picUrl
                this.author = po.author
                this.summary = po.summary
                this.publisher = po.publisher
                this.pubplace = po.publicPlace
                this.pubdate = if (po.publicDate != null) DateUtil.format(po.publicDate!!, DateUtil.PATTREN_DATE3) else ""
                this.page = po.page?.toString() ?: NumberUtils.INTEGER_ZERO.toString()
                this.price = po.price?.toDouble()?.div(Constants.hundred)?.toString()
                this.binding = po.binding
                this.isbn10 = po.isbn10Code
                this.keyword = po.keyword
                this.cip = po.cip
                this.edition = po.edition
                this.impression = po.impression
                this.language = po.language
                this.format = po.format
                this.`class` = po.bookClass
                this.isbn = po.isbnCode!!
                this.bookUid = po.bookUid!!
                this.bookStatus = po.status
                this.garden = gardenInfo?.levelName ?: ""
                this.classifyList = classifyPoList.mapNotNull { it.classify }.map {
                    BookClassifyDto.buildBookClassifyDto(BookClassifyEnum.getDescByCode(it), it)
                }
                this.ageGroups = ageGroupPos.mapNotNull { it.ageGroup }.map {
                    BookAgeGroupDto.buildBookAgeGroupDto(AgeGroupEnum.getDescByCode(it), it)
                }
            }
        }
        return Page(dto.page, dto.pageSize, pageQuery.total.toInt(), result)
    }

    /**
     * 分页查询
     */
    fun pageQueryBooks(dto: PageQueryBookDto): Page<PageQueryBookResultDto> {
        dto.apply {
            this.sort = SortByColumnEnum.findByName(dto.sort).code
        }
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
    fun getIsbnList(dto: PageQueryBookIsbnDto): Page<PageQueryBookIsbnResultEntity> {
        val pageQuery = bookRepository.pageQueryBookIsbns(dto)
        if (pageQuery.records.isNullOrEmpty()) return Page()
        return Page(dto.page, dto.pageSize, pageQuery.total.toInt(), pageQuery.records)
    }

}