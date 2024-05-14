package com.edu.book.domain.user.mapper

import com.edu.book.domain.area.enums.AreaTypeEnum
import com.edu.book.domain.user.dto.BindAccountDto
import com.edu.book.domain.user.dto.BindAccountRespDto
import com.edu.book.domain.user.dto.CreateAccountDto
import com.edu.book.domain.user.dto.ExportExcelAccountDto
import com.edu.book.domain.user.dto.PageQueryAccountDto
import com.edu.book.domain.user.dto.RegisterUserDto
import com.edu.book.domain.user.exception.AreaInfoNotExistException
import com.edu.book.infrastructure.constants.Constants
import com.edu.book.infrastructure.constants.Constants.number_five
import com.edu.book.infrastructure.po.area.AreaPo
import com.edu.book.infrastructure.po.area.LevelPo
import com.edu.book.infrastructure.po.book.BookBorrowFlowPo
import com.edu.book.infrastructure.po.user.BookAccountPo
import com.edu.book.infrastructure.po.user.BookAccountRoleRelationPo
import com.edu.book.infrastructure.po.user.BookAccountUserRelationPo
import com.edu.book.infrastructure.po.user.BookRoleBasicPo
import com.edu.book.infrastructure.po.user.BookRolePermissionRelationPo
import com.edu.book.infrastructure.po.user.BookUserPo
import com.edu.book.infrastructure.util.DateUtil
import com.edu.book.infrastructure.util.GeneratorShortUidUtil
import com.edu.book.infrastructure.util.UUIDUtil
import java.util.*
import org.apache.commons.lang3.BooleanUtils
import org.apache.commons.lang3.ObjectUtils
import org.apache.commons.lang3.StringUtils
import org.apache.commons.lang3.math.NumberUtils

object UserEntityMapper {

    /**
     * 构建
     */
    fun buildPageQueryAccountDto(areaInfos: List<AreaPo>, po: BookAccountPo, kindergartenInfo: LevelPo, gardenInfo: LevelPo, classInfo: LevelPo, bookBorrowFlowMap: Map<String, List<BookBorrowFlowPo>>): PageQueryAccountDto {
        val provinceInfo = areaInfos.filter { ObjectUtils.equals(it.areaType, AreaTypeEnum.PROVINCE.type) }.firstOrNull() ?: throw AreaInfoNotExistException()
        val cityInfo = areaInfos.filter { ObjectUtils.equals(it.areaType, AreaTypeEnum.CITY.type) }.firstOrNull() ?: throw AreaInfoNotExistException()
        val districtInfo = areaInfos.filter { ObjectUtils.equals(it.areaType, AreaTypeEnum.DISTRICT.type) }.firstOrNull() ?: throw AreaInfoNotExistException()
        val bookBorrowFlows = bookBorrowFlowMap.get(po.borrowCardId) ?: emptyList()
        return PageQueryAccountDto().apply {
            this.borrowCardId = po.borrowCardId
            this.studentName = po.studentName
            this.accountUid = po.accountUid
            this.password = po.password
            this.cashPledge = if (po.cashPledge == null) {
                NumberUtils.INTEGER_ZERO
            } else {
                po.cashPledge!! / Constants.hundred
            }
            this.expireTime = if (po.expireTime == null) {
                null
            } else {
                DateUtil.parse(po.expireTime!!, DateUtil.PATTREN_DATE)
            }
            this.parentPhone = po.parentPhone
            this.provinceName = provinceInfo.areaName
            this.cityName = cityInfo.areaName
            this.districtName = districtInfo.areaName
            this.kindergartenName = kindergartenInfo.levelName
            this.gardenName = gardenInfo.levelName
            this.className = classInfo.levelName
            this.borrowBoookCount = bookBorrowFlows.filter { (it.returnTime?.time ?: NumberUtils.LONG_ZERO) >= Date().time }.size
            this.overTimeBookCount = bookBorrowFlows.filter { (it.returnTime?.time ?: NumberUtils.LONG_ZERO) < Date().time }.size
        }
    }

    fun buildBookAccountRoleRelationPo(accountUid: String, visitorRoleInfo: BookRoleBasicPo): BookAccountRoleRelationPo {
        return BookAccountRoleRelationPo().apply {
            this.uid = UUIDUtil.createUUID()
            this.accountUid = accountUid
            this.roleUid = visitorRoleInfo.uid
            this.roleCode = visitorRoleInfo.roleCode
        }
    }

    fun buildExportExcelAccountDto(po: BookAccountPo, kindergartenInfo: LevelPo, gardenInfo: LevelPo, classInfo: LevelPo, areaInfos: List<AreaPo>): ExportExcelAccountDto {
        val provinceInfo = areaInfos.filter { ObjectUtils.equals(it.areaType, AreaTypeEnum.PROVINCE.type) }.firstOrNull() ?: throw AreaInfoNotExistException()
        val cityInfo = areaInfos.filter { ObjectUtils.equals(it.areaType, AreaTypeEnum.CITY.type) }.firstOrNull() ?: throw AreaInfoNotExistException()
        val districtInfo = areaInfos.filter { ObjectUtils.equals(it.areaType, AreaTypeEnum.DISTRICT.type) }.firstOrNull() ?: throw AreaInfoNotExistException()
        return ExportExcelAccountDto().apply {
            this.borrowCardId = po.borrowCardId
            this.studentName = po.studentName
            this.cashPledge = if (po.cashPledge == null) {
                NumberUtils.INTEGER_ZERO
            } else {
                po.cashPledge!! / Constants.hundred
            }
            this.expireTime = if (po.expireTime == null) {
                ""
            } else {
                DateUtil.parse(po.expireTime!!, DateUtil.PATTREN_DATE)
            }
            this.parentPhone = po.parentPhone
            this.provinceName = provinceInfo.areaName
            this.cityName = cityInfo.areaName
            this.districtName = districtInfo.areaName
            this.kindergartenName = kindergartenInfo.levelName
            this.gardenName = gardenInfo.levelName
            this.className = classInfo.levelName
        }
    }

    /**
     * 构建实体类
     */
    fun buildUploadBookAccountPo(uid: String, kindergartenInfo: LevelPo, classInfo: LevelPo, dto: CreateAccountDto, borrowCardId: String): BookAccountPo {
        val openBorrowService = dto.openBorrowService
        return BookAccountPo().apply {
            this.uid = uid
            this.accountUid = GeneratorShortUidUtil.generateShortUUID()
            this.password = GeneratorShortUidUtil.generateShortUUID()
            this.accountName = kindergartenInfo.levelName + "_" + classInfo.levelName + "_" + dto.studentName
            this.accountNickName = accountName
            this.expireTime = if (openBorrowService) {
                DateUtil.addMonths(Date(), number_five)
            } else {
                null
            }
            this.studentName = dto.studentName
            this.parentPhone = dto.parentPhone
            this.openBorrowService = dto.openBorrowService
            this.cashPledge = if (openBorrowService) {
                Constants.ten_thousand
            } else {
                null
            }
            this.borrowCardId = borrowCardId
            this.classUid = classInfo.uid!!
        }
    }

    /**
     * 构建实体类
     */
    fun bindBindAccountRespDto(accountPo: BookAccountPo, userPo: BookUserPo, rolePermissionRelations: List<BookRolePermissionRelationPo>, accountRoleRelationPo: BookAccountRoleRelationPo?): BindAccountRespDto {
        return BindAccountRespDto().apply {
            this.bind = BooleanUtils.toInteger(StringUtils.isNotBlank(userPo.associateAccount))
            this.phone = userPo.phone ?: ""
            this.userUid = userPo.uid!!
            this.openId = userPo.wechatUid!!
            this.username = userPo.name ?: accountPo.accountName ?: ""
            this.nickname = userPo.nickName ?: accountPo.accountNickName ?: ""
            this.permissionList = rolePermissionRelations.mapNotNull { it.permissionCode }.distinct()
            this.roleCode = accountRoleRelationPo?.roleCode ?: ""
            this.accountUid = accountPo.accountUid!!
            this.accountExpireTime = accountPo.expireTime!!.time
            this.unionId = userPo.unionId ?: ""
        }
    }

    /**
     * 构建参数
     */
    fun buildBindAccountUserRelationPo(userUid: String, accountUid: String, uid: String): BookAccountUserRelationPo {
        return BookAccountUserRelationPo().apply {
            this.uid = uid
            this.userUid = userUid
            this.accountUid = accountUid
        }
    }

    /**
     * 构建实体
     */
    fun buildUpdateUserPo(userPo: BookUserPo, accountPo: BookAccountPo, dto: BindAccountDto): BookUserPo {
        return BookUserPo().apply {
            this.uid = userPo.uid
            this.associateAccount = accountPo.accountUid
            this.phone = dto.phone
            this.unionId = dto.unionId
        }
    }

    /**
     * 构建实体
     */
    fun buildRegisterUserDto(finalUserPo: BookUserPo, rolePermissionRelations: List<BookRolePermissionRelationPo>, accountRoleRelationPo: BookAccountRoleRelationPo?, accountPo: BookAccountPo?, token: String): RegisterUserDto {
        return RegisterUserDto().apply {
            this.bind = BooleanUtils.toInteger(StringUtils.isNotBlank(finalUserPo.associateAccount))
            this.phone = finalUserPo.phone ?: ""
            this.userUid = finalUserPo.uid!!
            this.openId = finalUserPo.wechatUid!!
            this.username = finalUserPo.name ?: ""
            this.nickname = finalUserPo.nickName ?: ""
            this.token = token
            this.permissionList = rolePermissionRelations.mapNotNull { it.permissionCode }.distinct()
            this.roleCode = accountRoleRelationPo?.roleCode ?: ""
            this.accountUid = finalUserPo.associateAccount ?: ""
            this.accountExpireTime = accountPo?.expireTime?.time ?: NumberUtils.LONG_ZERO
            this.unionId = finalUserPo.unionId ?: ""
        }
    }

    /**
     * 构建实体
     */
    fun registerUserBuildUserPo(openId: String): BookUserPo {
        return BookUserPo().apply {
            this.uid = UUIDUtil.createUUID()
            this.name = StringUtils.EMPTY
            this.nickName = StringUtils.EMPTY
            this.wechatUid = openId
            this.phone = ""
            this.associateAccount = StringUtils.EMPTY
        }
    }

}