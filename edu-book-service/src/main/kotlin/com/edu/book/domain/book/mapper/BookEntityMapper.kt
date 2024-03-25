package com.edu.book.domain.book.mapper

import com.alibaba.fastjson.JSON
import com.edu.book.api.vo.isbn.IsbnBookInfoRespDto
import com.edu.book.domain.book.dto.BookDetailDto
import com.edu.book.domain.book.dto.BookDto
import com.edu.book.domain.book.dto.BookSellDto
import com.edu.book.domain.book.dto.ScanBookCodeInStorageDto
import com.edu.book.domain.book.dto.ScanIsbnCodeBookDto
import com.edu.book.domain.book.enums.BookDetailStatusEnum
import com.edu.book.infrastructure.constants.Constants.hundred
import com.edu.book.infrastructure.po.book.BookDetailClassifyPo
import com.edu.book.infrastructure.po.book.BookDetailPo
import com.edu.book.infrastructure.po.book.BookPo
import com.edu.book.infrastructure.util.DateUtil
import com.edu.book.infrastructure.util.DateUtil.Companion.PATTREN_DATE
import com.edu.book.infrastructure.util.DateUtil.Companion.PATTREN_DATE3
import com.edu.book.infrastructure.util.MapperUtil
import com.edu.book.infrastructure.util.UUIDUtil
import java.sql.Timestamp
import java.util.Date

object BookEntityMapper {

    /**
     * 构建实体类
     */
//    fun buildBookDetailDto(): BookDetailDto {
//
//    }

    /**
     * 构建实体类
     */
    fun buildScanBookCodeUpdateBookPo(dto: ScanBookCodeInStorageDto, bookPo: BookPo): BookPo {
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
    fun buildBookDetailPo(dto: ScanBookCodeInStorageDto): BookDetailPo {
        return BookDetailPo().apply {
            this.uid = UUIDUtil.createUUID()
            this.isbnCode = dto.isbn
            this.bookUid = dto.bookUid
            this.status = BookDetailStatusEnum.IN_STORAGE.status
            this.inStorageTime = Timestamp(Date().time)
            this.garden = dto.garden
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