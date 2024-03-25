package com.edu.book.domain.book.mapper

import com.edu.book.api.vo.isbn.IsbnBookInfoRespDto
import com.edu.book.domain.book.dto.BookDto
import com.edu.book.domain.book.dto.BookSellDto
import com.edu.book.domain.book.dto.ScanIsbnCodeBookDto
import com.edu.book.infrastructure.constants.Constants.hundred
import com.edu.book.infrastructure.util.DateUtil
import com.edu.book.infrastructure.util.DateUtil.Companion.PATTREN_DATE
import com.edu.book.infrastructure.util.DateUtil.Companion.PATTREN_DATE3
import com.edu.book.infrastructure.util.MapperUtil

object BookEntityMapper {

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