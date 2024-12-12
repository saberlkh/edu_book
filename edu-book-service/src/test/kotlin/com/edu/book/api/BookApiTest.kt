package com.edu.book.api

import com.edu.book.EduBookServiceApplication
import com.edu.book.application.task.BookBorrowStatusUnsetTask
import com.edu.book.domain.book.service.BookDomainService
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

/**
 * @Auther: liukaihua
 * @Date: 2024/6/3 17:12
 * @Description:
 */

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [EduBookServiceApplication::class])
class BookApiTest {

    @Autowired
    private lateinit var bookDomainService: BookDomainService

    @Autowired
    private lateinit var bookBorrowStatusUnsetTask: BookBorrowStatusUnsetTask

    @Test
    fun `定时任务`() {
        bookBorrowStatusUnsetTask.unsetBookBorrowStatus()
    }

}