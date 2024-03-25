package com.edu.book.domain.book.service

import com.alibaba.fastjson.JSON
import com.edu.book.domain.book.dto.BookDto
import com.edu.book.domain.book.repository.BookRepository
import com.edu.book.domain.book.repository.BookSellRepository
import com.edu.book.infrastructure.po.book.BookPo
import com.edu.book.infrastructure.po.book.BookSellPo
import com.edu.book.infrastructure.util.MapperUtil
import com.edu.book.infrastructure.util.UUIDUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

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

    /**
     * 根据isbn查询书
     */
    fun findBookByIsbnCode(isbnCode: String): BookDto? {
        val po = bookRepository.findByIsbnCode(isbnCode) ?: return null
        return MapperUtil.map(BookDto::class.java, po)
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

}