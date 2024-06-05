package com.edu.book.domain.read.mapper

import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.edu.book.domain.read.dto.LikeReadCircleDto
import com.edu.book.domain.read.dto.PageReadCircleDto
import com.edu.book.domain.read.dto.PublishReadCircleDto
import com.edu.book.domain.read.dto.ReadCircleAttachmentDto
import com.edu.book.domain.read.dto.ReadCircleCommentDto
import com.edu.book.domain.read.dto.ReadCircleLikeDto
import com.edu.book.infrastructure.po.area.LevelPo
import com.edu.book.infrastructure.po.read.BookReadCircleAttachmentPo
import com.edu.book.infrastructure.po.read.BookReadCircleCommentFlowPo
import com.edu.book.infrastructure.po.read.BookReadCircleLikeFlowPo
import com.edu.book.infrastructure.po.read.BookReadCirclePo
import com.edu.book.infrastructure.po.upload.UploadFilePo
import com.edu.book.infrastructure.po.user.BookAccountPo
import com.edu.book.infrastructure.po.user.BookUserPo
import com.edu.book.infrastructure.util.UUIDUtil

object ReadCircleEntityMapper {

    /**
     * 构建
     */
    fun buildLikeReadCircleFlowPo(dto: LikeReadCircleDto): BookReadCircleLikeFlowPo {
        return BookReadCircleLikeFlowPo().apply {
            this.uid = UUIDUtil.createUUID()
            this.readCircleUid = dto.circleUid
            this.userUid = dto.userUid
            this.likeStatus = dto.likeStatus
        }
    }

    /**
     * 构建
     */
    fun buildPageQueryCircleDto(pageQuery: Page<BookReadCirclePo>, userPoMap: Map<String, BookUserPo>,
                                accountPoMap: Map<String, BookAccountPo>, classMap: Map<String, LevelPo>,
                                gradeMap: Map<String, LevelPo>, gardenMap: Map<String, LevelPo>,
                                kindergartenMap: Map<String, LevelPo>, readCircleAttachmentMap: Map<String, List<BookReadCircleAttachmentPo>>,
                                likeMap: Map<String, List<BookReadCircleLikeFlowPo>>, commentMap: Map<String, List<BookReadCircleCommentFlowPo>>,
                                uploadInfoMap: Map<String, UploadFilePo>): List<PageReadCircleDto> {
        return pageQuery.records.map {
            val createCircleUser = userPoMap.get(it.userUid)
            val createCircleAccount = accountPoMap.get(it.accountUid)
            val classInfo = classMap.get(createCircleAccount?.classUid)
            val gradeInfo = gradeMap.get(classInfo?.parentUid)
            val gardenInfo = gardenMap.get(gradeInfo?.parentUid)
            val kindergartenInfo = kindergartenMap.get(gardenInfo?.parentUid)
            val circleAttachments = readCircleAttachmentMap.get(it.uid) ?: emptyList()
            val circleLikes = likeMap.get(it.uid) ?: emptyList()
            val circleComments = commentMap.get(it.uid) ?: emptyList()
            PageReadCircleDto().apply {
                this.readCircleUid = it.uid!!
                this.readText = it.readText ?: ""
                this.userUid = it.userUid!!
                this.username = createCircleAccount?.accountName ?: ""
                this.nickname = createCircleAccount?.accountNickName ?: ""
                this.studentName = createCircleAccount?.studentName ?: ""
                this.classUid = classInfo?.uid ?: ""
                this.className = classInfo?.levelName ?: ""
                this.gradeUid = gradeInfo?.uid ?: ""
                this.gradeName = gradeInfo?.levelName ?: ""
                this.gardenUid = gardenInfo?.uid ?: ""
                this.gardenName = gardenInfo?.levelName ?: ""
                this.kindergartenUid = kindergartenInfo?.uid ?: ""
                this.kindergartenName = kindergartenInfo?.levelName ?: ""
                this.attachments = circleAttachments.map {
                    val uploadInfo = uploadInfoMap.get(it.fileKey)
                    ReadCircleAttachmentDto().apply {
                        this.fileKey = it.fileKey!!
                        this.downloadUrl = uploadInfo?.filePath ?: ""
                        this.fileType = uploadInfo?.fileType ?: ""
                    }
                }
                this.likes = circleLikes.map {
                    val likeUserInfo = userPoMap.get(it.userUid)
                    val likeAccountInfo = accountPoMap.get(likeUserInfo?.associateAccount)
                    ReadCircleLikeDto().apply {
                        this.likeUserUid = it.userUid!!
                        this.likeUsername = likeAccountInfo?.accountName ?: ""
                        this.likeUserNickname = likeAccountInfo?.accountNickName ?: ""
                    }
                }
                this.comments = circleComments.map {
                    val commentUserInfo = userPoMap.get(it.commentUserUid)
                    val commentAccountInfo = accountPoMap.get(commentUserInfo?.associateAccount)
                    val commentedUserInfo = userPoMap.get(it.commentedUserUid)
                    val commentedAccountInfo = accountPoMap.get(commentedUserInfo?.associateAccount)
                    ReadCircleCommentDto().apply {
                        this.commentText = it.comment!!
                        this.commentUserUid = it.commentUserUid!!
                        this.commentUsername = commentAccountInfo?.accountName ?: ""
                        this.commentUserNickname = commentAccountInfo?.accountNickName ?: ""
                        this.commentUid = it.uid!!
                        this.commentedUserUid = it.commentedUserUid!!
                        this.commentedUsername = commentedAccountInfo?.accountName ?: ""
                        this.commentedUserNickname = commentedAccountInfo?.accountNickName ?: ""
                        this.parentCommentUid = it.parentCommentUid!!
                    }
                }
            }
        }
    }

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