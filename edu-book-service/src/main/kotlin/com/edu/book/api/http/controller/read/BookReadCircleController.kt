package com.edu.book.api.http.controller.read

import com.edu.book.api.http.service.BookReadCircleWebService
import com.edu.book.api.vo.read.PublishReadCircleVo
import com.edu.book.infrastructure.anno.Response
import javax.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @Auther: liukaihua
 * @Date: 2024/6/4 19:36
 * @Description:
 */

@RestController
@RequestMapping("/read")
@Response
class BookReadCircleController {

    @Autowired
    private lateinit var bookReadCircleWebService: BookReadCircleWebService

    /**
     * 借书
     */
    @PostMapping("/v1/circle")
    fun publishReadCircle(@RequestBody @Valid vo: PublishReadCircleVo) {
        bookReadCircleWebService.publishReadCircle(vo)
    }

}