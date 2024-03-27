package com.edu.book.infrastructure.util.page

import com.edu.book.infrastructure.util.page.PageUtil.Companion.START_PAGE_NUM
import java.io.Serializable

/**
 * @Auther: liukaihua
 * @Date: 2024/3/26 19:50
 * @Description:
 */
open class Page<E : Serializable> : Serializable, Iterable<E> {

    var page: Int = 0
    var pageSize: Int = 0
    var totalCount: Int = 0
    var result: List<E>? = emptyList()

    constructor(){}

    constructor(page: Int, pageSize: Int, totalCount: Int, result: List<E>?) {
        this.page = page
        this.result = result
        this.pageSize = pageSize
        this.totalCount = totalCount
        thisPage = PageUtil.computePage(page, pageSize, totalCount)
    }

    var thisPage: Int = 0

    val isFirstPage: Boolean get() = this.thisPage == START_PAGE_NUM

    val isLastPage: Boolean get() = this.thisPage >= this.lastPage

    val isHasNextPage: Boolean get() = this.lastPage > this.thisPage

    val isHasPreviousPage: Boolean get() = this.thisPage > START_PAGE_NUM

    val lastPage: Int get() = PageUtil.computeLastPage(this.totalCount, this.pageSize)

    val thisPageFirstElementNum: Int get() = (this.thisPage - 1) * this.pageSize + 1

    val nextPage: Int get() = this.thisPage + 1

    val previousPage: Int get() = this.thisPage - 1

    val thisPageLastElementNum: Int get() {
        val fullPage = this.thisPageFirstElementNum + this.pageSize - 1
        return if (this.totalCount < fullPage) this.totalCount else fullPage
    }

    val linkPages: List<Int> get() = PageUtil.generateLinkPages(this.thisPage, this.lastPage, 10)

    val firstResult: Int get() = PageUtil.getFirstResult(this.thisPage, this.pageSize)

    override fun iterator(): Iterator<E> {
        return if (this.result == null) {
            emptyList<E>().iterator()
        } else {
            this.result!!.iterator()
        }
    }
}