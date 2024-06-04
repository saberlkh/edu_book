package com.edu.book.domain.read.mapper

import com.edu.book.domain.read.dto.PublishReadCircleDto
import com.edu.book.infrastructure.po.area.LevelPo
import com.edu.book.infrastructure.po.read.BookReadCircleAttachmentPo
import com.edu.book.infrastructure.po.read.BookReadCirclePo
import com.edu.book.infrastructure.po.user.BookAccountPo
import com.edu.book.infrastructure.util.UUIDUtil

object ReadCircleEntityMapper {

    /**
     * 构建实体
     */
    fun buildPublishBookReadCircleAttachment(dto: PublishReadCircleDto, readCircleUid: String): List<BookReadCircleAttachmentPo> {
        return dto.attachments.map {
            BookReadCircleAttachmentPo().apply {
                this.uid = UUIDUtil.createUUID()
                this.readCircleUid = readCircleUid
                this.fileKey = it
            }
        }
    }

    /**
     * 构建实体类
     */
    fun buildPublishBookReadCirclePo(readCircleUid: String, dto: PublishReadCircleDto, accountPo: BookAccountPo, gardenInfo: LevelPo): BookReadCirclePo {
        return BookReadCirclePo().apply {
            this.uid = readCircleUid
            this.readText = dto.readText
            this.userUid = dto.userUid
            this.accountUid = accountPo.accountUid
            this.gardenUid = gardenInfo.uid
        }
    }

}