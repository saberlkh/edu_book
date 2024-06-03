package com.edu.book.domain.read.service

import com.edu.book.domain.read.repository.BookReadCircleAttachmentRepository
import com.edu.book.domain.read.repository.BookReadCircleCommentFlowRepository
import com.edu.book.domain.read.repository.BookReadCircleLikeFlowRepository
import com.edu.book.domain.read.repository.BookReadCircleRepository
import com.edu.book.domain.upload.repository.UploadFileRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @Auther: liukaihua
 * @Date: 2024/6/3 22:55
 * @Description:
 */

@Service
class ReadCircleDomainService {

    private val logger = LoggerFactory.getLogger(ReadCircleDomainService::class.java)

    @Autowired
    private lateinit var bookReadCircleAttachmentRepository: BookReadCircleAttachmentRepository

    @Autowired
    private lateinit var bookReadCircleRepository: BookReadCircleRepository

    @Autowired
    private lateinit var bookReadCircleLikeFlowRepository: BookReadCircleLikeFlowRepository

    @Autowired
    private lateinit var bookReadCircleCommentFlowRepository: BookReadCircleCommentFlowRepository

    @Autowired
    private lateinit var uploadFileRepository: UploadFileRepository



}