package com.edu.book.domain.book.mapper

import com.alibaba.fastjson.JSON
import com.edu.book.api.vo.isbn.IsbnBookInfoRespDto
import com.edu.book.domain.book.dto.BookAgeGroupDto
import com.edu.book.domain.book.dto.BookClassifyDto
import com.edu.book.domain.book.dto.BookDetailDto
import com.edu.book.domain.book.dto.BookDto
import com.edu.book.domain.book.dto.BookSellDto
import com.edu.book.domain.book.dto.BorrowBookDto
import com.edu.book.domain.book.dto.CollectBookDto
import com.edu.book.domain.book.dto.PageQueryBookCollectDto
import com.edu.book.domain.book.dto.PageQueryBorrowBookResultDto
import com.edu.book.domain.book.dto.ScanBookCodeInStorageParam
import com.edu.book.domain.book.dto.ScanIsbnCodeBookDto
import com.edu.book.domain.book.enums.AgeGroupEnum
import com.edu.book.domain.book.enums.BookBorrowStatusEnum
import com.edu.book.domain.book.enums.BookClassifyEnum
import com.edu.book.domain.book.enums.BookDetailStatusEnum
import com.edu.book.infrastructure.constants.Constants.hundred
import com.edu.book.infrastructure.constants.Constants.number_three
import com.edu.book.infrastructure.po.area.LevelPo
import com.edu.book.infrastructure.po.book.BookBorrowFlowPo
import com.edu.book.infrastructure.po.book.BookCollectFlowPo
import com.edu.book.infrastructure.po.book.BookDetailAgePo
import com.edu.book.infrastructure.po.book.BookDetailClassifyPo
import com.edu.book.infrastructure.po.book.BookDetailPo
import com.edu.book.infrastructure.po.book.BookPo
import com.edu.book.infrastructure.po.user.BookAccountPo
import com.edu.book.infrastructure.po.user.BookUserPo
import com.edu.book.infrastructure.util.DateUtil
import com.edu.book.infrastructure.util.DateUtil.Companion.PATTREN_DATE3
import com.edu.book.infrastructure.util.MapperUtil
import com.edu.book.infrastructure.util.UUIDUtil
import java.sql.Timestamp
import java.util.Date
import org.apache.commons.lang3.BooleanUtils
import org.apache.commons.lang3.math.NumberUtils

object BookEntityMapper {

    /**
     * 构建实体类
     */
    fun buildBookCollectPo(bookPo: BookDetailPo, userPo: BookUserPo, accountPo: BookAccountPo?, dto: CollectBookDto): BookCollectFlowPo {
        return BookCollectFlowPo().apply {
            this.uid = UUIDUtil.createUUID()
            this.isbnCode = bookPo.isbnCode
            this.bookUid = bookPo.bookUid
            this.userUid = userPo.uid
            this.accountUid = accountPo?.accountUid
            this.collectStatus = BooleanUtils.toBoolean(dto.collectStatus!!)
        }
    }

    /**
     * 构建实体类
     */
    fun buildBookDetailDto(detailPo: BookDetailPo, classifyList: List<BookDetailClassifyPo>, ageGroups: List<BookDetailAgePo>, collectFlowPo: BookCollectFlowPo?): BookDetailDto {
        return MapperUtil.map(BookDetailDto::class.java, detailPo, excludes = listOf("price")).apply {
            this.pic = detailPo.picUrl
            this.pubplace = detailPo.publicPlace
            this.pubdate = if (detailPo.publicDate != null) DateUtil.format(detailPo.publicDate!!, PATTREN_DATE3) else ""
            this.isbn = detailPo.isbnCode!!
            this.isbn10 = detailPo.isbn10Code
            this.`class` = detailPo.bookClass
            this.subtitle = detailPo.subTitle
            this.price = detailPo.price?.toDouble()?.div(hundred)?.toString()
            this.`class` = detailPo.bookClass
            this.bookUid = detailPo.bookUid!!
            this.garden = detailPo.garden ?: ""
            this.bookStatus = detailPo.status
            this.classifyList = classifyList.mapNotNull { it.classify }.map {
                BookClassifyDto.buildBookClassifyDto(BookClassifyEnum.getDescByCode(it), it)
            }
            this.ageGroups = ageGroups.mapNotNull { it.ageGroup }.map {
                BookAgeGroupDto.buildBookAgeGroupDto(AgeGroupEnum.getDescByCode(it), it)
            }
            val booleanCollectStatus = collectFlowPo?.collectStatus ?: false
            this.collectStatus = BooleanUtils.toInteger(booleanCollectStatus)
        }
    }

    /**
     * 构建实体类
     */
    fun buildScanBookCodeUpdateBookPo(dto: ScanBookCodeInStorageParam, bookPo: BookPo, gardenInfo: LevelPo): BookPo {
        return bookPo.apply {
            this.isbnCode = dto.isbn
            this.title = dto.title ?: bookPo.title
            this.subTitle = dto.subtitle ?: bookPo.subTitle
            this.picUrl = dto.pic ?: bookPo.picUrl
            this.author = dto.author ?: bookPo.author
            this.summary = if (dto.summary.isNullOrBlank()) {
                JSON.toJSONString(bookPo.summary)
            } else {
                JSON.toJSONString(dto.summary)
            }
            this.publisher = dto.publisher ?: bookPo.publisher
            this.publicPlace = dto.pubplace ?: bookPo.publicPlace
            this.publicDate = if (dto.pubdate != null) {
                DateUtil.convertToDate(dto.pubdate, PATTREN_DATE3)
            } else {
                bookPo.publicDate
            }
            this.page = if (dto.page.isNullOrBlank()){
                bookPo.page
            } else {
                dto.page!!.toInt()
            }
            this.price = if (dto.price.isNullOrBlank()) {
                bookPo.price
            } else {
                dto.price!!.toDouble().times(hundred).toInt()
            }
            this.binding = dto.binding ?: bookPo.binding
            this.isbn10Code = dto.isbn10 ?: bookPo.isbn10Code
            this.keyword = dto.keyword ?: bookPo.keyword
            this.cip = dto.cip ?: bookPo.cip
            this.edition = dto.edition ?: bookPo.edition
            this.impression = dto.impression ?: bookPo.impression
            this.language = dto.language ?: bookPo.language
            this.format = dto.format ?: bookPo.format
            this.bookClass = dto.`class` ?: bookPo.bookClass
            this.garden = gardenInfo.levelName
            this.gardenUid = gardenInfo.uid
        }
    }

    /**
     * 构建
     */
    fun buildScanIsbnCodeBookDto(bookDto: BookDto): ScanIsbnCodeBookDto {
        return MapperUtil.map(ScanIsbnCodeBookDto::class.java, bookDto, excludes = listOf("price")).apply {
            this.pic = bookDto.picUrl
            this.pubplace = bookDto.publicPlace
            this.pubdate = if (bookDto.publicDate != null) DateUtil.format(bookDto.publicDate!!, PATTREN_DATE3) else ""
            this.isbn = bookDto.isbnCode
            this.isbn10 = bookDto.isbn10Code
            this.`class` = bookDto.bookClass
            this.subtitle = bookDto.subTitle
            this.price = bookDto.price?.toDouble()?.div(hundred)?.toString()
            this.`class` = bookDto.bookClass
        }
    }

    /**
     * 构建实体类
     */
    fun buildBookDetailPo(dto: ScanBookCodeInStorageParam, gardenInfo: LevelPo): BookDetailPo {
        return MapperUtil.map(BookDetailPo::class.java, dto, excludes = listOf("price")).apply {
            this.uid = UUIDUtil.createUUID()
            this.bookUid = dto.bookUid
            this.status = BookDetailStatusEnum.IN_STORAGE.status
            this.inStorageTime = Timestamp(Date().time)
            this.garden = gardenInfo.levelName
            this.gardenUid = gardenInfo.uid
            this.subTitle = dto.subtitle
            this.picUrl = dto.pic
            this.publicPlace = dto.pubplace
            this.publicDate = if (dto.pubdate.isNullOrBlank()) {
                null
            } else {
                DateUtil.convertToDate(dto.pubdate, PATTREN_DATE3)
            }
            this.price = dto.price?.toDouble()?.times(hundred)?.toInt()
            this.isbn10Code = dto.isbn10
            this.isbnCode = dto.isbn
            this.bookClass = dto.`class`
            this.summary = JSON.toJSONString(dto.summary)
        }
    }

    /**
     * 构建实体
     */
    fun buildPageQueryBookCollectDto(collectPo: BookCollectFlowPo, bookDetailPo: BookDetailPo?, userPo: BookUserPo): PageQueryBookCollectDto {
        return PageQueryBookCollectDto().apply {
            this.bookUid = collectPo.bookUid!!
            this.picUrl = bookDetailPo?.picUrl
            this.title = bookDetailPo?.title
            this.author = bookDetailPo?.author
            this.userUid = userPo.uid
            this.summary = bookDetailPo?.summary
            this.subTitle = bookDetailPo?.subTitle
        }
    }

    /**
     * 构建实体类
     */
    fun buildPageQueryBorrowBookResultDto(borrowFlowPo: BookBorrowFlowPo, bookDetailPo: BookDetailPo?): PageQueryBorrowBookResultDto {
        return PageQueryBorrowBookResultDto().apply {
            this.bookUid = borrowFlowPo.bookUid!!
            this.picUrl = bookDetailPo?.picUrl
            this.title = bookDetailPo?.title
            this.author = bookDetailPo?.author
            this.borrowTime = borrowFlowPo.borrowTime?.time
            this.returnTime = borrowFlowPo.returnTime?.time
            this.returnDay = if (borrowFlowPo.returnTime != null && borrowFlowPo.returnTime!!.time > Date().time) {
                DateUtil.calDateDay(borrowFlowPo.returnTime)
            } else {
                NumberUtils.INTEGER_ZERO
            }
            this.borrowStatus = if (this.returnDay > NumberUtils.INTEGER_ZERO && this.returnDay <= number_three) {
                BookBorrowStatusEnum.ABOUT_TO_EXPIRE.status
            } else {
                borrowFlowPo.borrowStatus
            }
            this.subTitle = bookDetailPo?.subTitle
        }
    }

    /**
     * 构建实体类
     */
    fun buildBookBorrowFlowPo(bookInfo: BookPo, bookDetailInfo: BookDetailPo, userInfoPo: BookUserPo, dto: BorrowBookDto, accountInfo: BookAccountPo, gardenInfo: LevelPo): BookBorrowFlowPo {
        return BookBorrowFlowPo().apply {
            this.uid = UUIDUtil.createUUID()
            this.isbnCode = bookInfo.isbnCode
            this.bookUid = bookDetailInfo.bookUid
            this.borrowUserUid = userInfoPo.uid
            this.borrowTime = Date()
            this.borrowCardId = dto.borrowCardId
            this.accountUid = accountInfo.accountUid
            this.borrowStatus = BookBorrowStatusEnum.BORROWER.status
            this.returnTime = DateUtil.addMonths(Date(), NumberUtils.INTEGER_ONE)
            this.gardenUid = gardenInfo.uid
        }
    }

    /**
     * 构建实体
     */
    fun buildBookDetailAgeGroupPos(dto: ScanBookCodeInStorageParam): List<BookDetailAgePo> {
        return dto.ageGroups.map {
            BookDetailAgePo().apply {
                this.uid = UUIDUtil.createUUID()
                this.isbnCode = dto.isbn
                this.ageGroup = it
                this.bookUid = dto.bookUid
            }
        }
    }

    /**
     * 构建列表
     */
    fun buildBookDetailClassifyPos(dto: ScanBookCodeInStorageParam): List<BookDetailClassifyPo> {
        return dto.classifyList.map {
            BookDetailClassifyPo().apply {
                this.uid = UUIDUtil.createUUID()
                this.isbnCode = dto.isbn
                this.classify = it
                this.bookUid = dto.bookUid
            }
        }
    }

    /**
     * 构建实体类
     */
    fun buildScanBookCodeInsertBookPo(dto: ScanBookCodeInStorageParam): BookPo {
        return MapperUtil.map(BookPo::class.java, dto, excludes = listOf("price")).apply {
            this.uid = UUIDUtil.createUUID()
            this.subTitle = dto.subtitle
            this.picUrl = dto.pic
            this.publicPlace = dto.pubplace
            this.publicDate = if (dto.pubdate.isNullOrBlank()) {
                null
            } else {
                DateUtil.convertToDate(dto.pubdate, PATTREN_DATE3)
            }
            this.price = dto.price?.toDouble()?.times(hundred)?.toInt()
            this.isbn10Code = dto.isbn10
            this.isbnCode = dto.isbn
            this.bookClass = dto.`class`
            this.summary = JSON.toJSONString(dto.summary)
        }
    }

    /**
     * 构建
     */
    fun buildBookDto(respDto: IsbnBookInfoRespDto): BookDto {
        return MapperUtil.map(BookDto::class.java, respDto, excludes = listOf("price")).apply {
            this.subTitle = respDto.subtitle
            this.picUrl = respDto.pic
            this.publicPlace = respDto.pubplace
            this.publicDate = DateUtil.convertToDate(respDto.pubdate, PATTREN_DATE3)
            this.price = respDto.price?.toDouble()?.times(hundred)?.toInt()
            this.isbn10Code = respDto.isbn10
            this.isbnCode = respDto.isbn
            this.bookClass = respDto.`class`
            this.sellList = respDto.sellerlist?.map {
                BookSellDto().apply {
                    this.link = it.link
                    this.price = it.price?.toDouble()?.times(hundred)?.toInt()
                    this.seller = it.seller
                }
            } ?: emptyList()
        }
    }

}