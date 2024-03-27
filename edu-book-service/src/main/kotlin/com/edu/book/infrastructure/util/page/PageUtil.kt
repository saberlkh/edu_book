package com.edu.book.infrastructure.util.page

import java.util.ArrayList

/**
 * @Auther: liukaihua
 * @Date: 2024/3/26 19:50
 * @Description:
 */
class PageUtil {

    init {
        throw UnsupportedOperationException()
    }

    companion object {

        const val MIN_PAGE_SIZE = 0
        const val START_PAGE_NUM = 1

        fun getFirstResult(pageNumber: Int, pageSize: Int): Int {
            return if (pageSize <= MIN_PAGE_SIZE) MIN_PAGE_SIZE else (pageNumber - 1) * pageSize
        }

        /** 生成链接的页码  */
        fun generateLinkPages(currentPage: Int, lastPage: Int, count: Int): List<Int> {
            val avg = count / 2
            var startPage = currentPage - avg
            if (startPage <= 0) {
                startPage = START_PAGE_NUM
            }

            var endPage = startPage + count - 1
            if (endPage > lastPage) {
                endPage = lastPage
            }

            if (endPage - startPage < count) {
                startPage = endPage - count
                if (startPage <= 0) {
                    startPage = START_PAGE_NUM
                }
            }

            val result = ArrayList<Int>()
            for (i in startPage..endPage) {
                result.add(i)
            }

            return result
        }

        /** 计算最后的页码  */
        fun computeLastPage(totalElements: Int, pageSize: Int): Int {
            if (pageSize <= MIN_PAGE_SIZE) {
                return START_PAGE_NUM
            }
            val result = if (totalElements % pageSize == 0)
                totalElements / pageSize
            else
                totalElements / pageSize + 1
            return if (result <= START_PAGE_NUM) START_PAGE_NUM else result
        }

        /** 计算当前页码  */
        fun computePage(pageNum: Int, pageSize: Int, totalElements: Int): Int {
            if (pageNum <= START_PAGE_NUM || pageSize <= MIN_PAGE_SIZE) {
                return START_PAGE_NUM
            }
            val lastPageNum = computeLastPage(totalElements, pageSize)
            return if (pageNum <= lastPageNum) pageNum else lastPageNum
        }
    }
}