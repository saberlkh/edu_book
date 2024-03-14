package com.edu.book.domain.user.mapper

import com.edu.book.domain.dto.RegisterUserDto
import com.edu.book.infrastructure.po.user.BookAccountPo
import com.edu.book.infrastructure.po.user.BookAccountRoleRelationPo
import com.edu.book.infrastructure.po.user.BookRolePermissionRelationPo
import com.edu.book.infrastructure.po.user.BookUserPo
import com.edu.book.infrastructure.util.UUIDUtil
import org.apache.commons.lang3.BooleanUtils
import org.apache.commons.lang3.StringUtils
import org.apache.commons.lang3.math.NumberUtils

object UserEntityMapper {

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