package com.edu.book.domain.book.mapper

import com.alibaba.fastjson.JSON
import com.edu.book.api.vo.isbn.IsbnBookInfoRespDto
import com.edu.book.domain.book.dto.BookDetailDto
import com.edu.book.domain.book.dto.BookDto
import com.edu.book.domain.book.dto.BookSellDto
import com.edu.book.domain.book.dto.BorrowBookDto
import com.edu.book.domain.book.dto.ScanBookCodeInStorageDto
import com.edu.book.domain.book.dto.ScanIsbnCodeBookDto
import com.edu.book.domain.book.enums.BookBorrowStatusEnum
import com.edu.book.domain.book.enums.BookDetailStatusEnum
import com.edu.book.infrastructure.constants.Constants.hundred
import com.edu.book.infrastructure.po.area.LevelPo
import com.edu.book.infrastructure.po.book.BookBorrowFlowPo
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
import org.apache.commons.lang3.math.NumberUtils

object BookEntityMapper {

    /**
     * 构建实体类
     */
    fun buildBookDetailDto(detailPo: BookDetailPo, bookPo: BookPo, classifyList: List<BookDetailClassifyPo>, ageGroups: List<BookDetailAgePo>): BookDetailDto {
        return MapperUtil.map(BookDetailDto::class.java, bookPo, excludes = listOf("price")).apply {
            this.pic = bookPo.picUrl
            this.pubplace = bookPo.publicPlace
            this.pubdate = if (bookPo.publicDate != null) DateUtil.format(bookPo.publicDate!!, PATTREN_DATE3) else ""
            this.isbn = bookPo.isbnCode!!
            this.isbn10 = bookPo.isbn10Code
            this.`class` = bookPo.bookClass
            this.subtitle = bookPo.subTitle
            this.price = bookPo.price?.toDouble()?.div(hundred)?.toString()
            this.`class` = bookPo.bookClass
            this.bookUid = detailPo.bookUid!!
            this.garden = detailPo.garden ?: ""
            this.bookStatus = detailPo.status
            this.classify = classifyList.mapNotNull { it.classify }
            this.ageGroups = ageGroups.mapNotNull { it.ageGroup }
        }
    }

    /**
     * 构建实体类
     */
    fun buildScanBookCodeUpdateBookPo(dto: ScanBookCodeInStorageDto, bookPo: BookPo, gardenInfo: LevelPo): BookPo {
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
    fun buildBookDetailPo(dto: ScanBookCodeInStorageDto, gardenInfo: LevelPo): BookDetailPo {
        return BookDetailPo().apply {
            this.uid = UUIDUtil.createUUID()
            this.isbnCode = dto.isbn
            this.bookUid = dto.bookUid
            this.status = BookDetailStatusEnum.IN_STORAGE.status
            this.inStorageTime = Timestamp(Date().time)
            this.garden = gardenInfo.levelName
            this.gardenUid = gardenInfo.uid
        }
    }

    /**
     * 构建实体类
     */
    fun buildBookBorrowFlowPo(bookInfo: BookPo, bookDetailInfo: BookDetailPo, userInfoPo: BookUserPo, dto: BorrowBookDto, accountInfo: BookAccountPo): BookBorrowFlowPo {
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
        }
    }

    /**
     * 构建实体
     */
    fun buildBookDetailAgeGroupPos(dto: ScanBookCodeInStorageDto): List<BookDetailAgePo> {
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
    fun buildBookDetailClassifyPos(dto: ScanBookCodeInStorageDto): List<BookDetailClassifyPo> {
        return dto.classify.map {
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
    fun buildScanBookCodeInsertBookPo(dto: ScanBookCodeInStorageDto): BookPo {
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