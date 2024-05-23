package com.edu.book.application.service

import com.edu.book.domain.user.dto.BindAccountDto
import com.edu.book.domain.user.dto.BindAccountRespDto
import com.edu.book.domain.user.dto.PageQueryAccountDto
import com.edu.book.domain.user.dto.PageQueryAccountParamDto
import com.edu.book.domain.user.dto.RegisterUserDto
import com.edu.book.domain.user.dto.UnbindAccountDto
import com.edu.book.domain.user.dto.UnbindAccountRespDto
import com.edu.book.domain.user.dto.UploadFileCreateAccountDto
import com.edu.book.domain.user.dto.UserDto
import com.edu.book.domain.user.service.UserDomainService
import com.edu.book.infrastructure.util.page.Page
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @Auther: liukaihua
 * @Date: 2024/3/13 23:51
 * @Description:
 */

@Service
class UserAppService {

    @Autowired
    private lateinit var userDomainService: UserDomainService

    /**
     * 分页查询
     */
    fun pageQueryAccountListByClass(param: PageQueryAccountParamDto): Page<PageQueryAccountDto> {
        return userDomainService.pageQueryAccountListByClass(param)
    }

    /**
     * 生成账号
     */
    fun uploadFileCreateAccount(accountDto: UploadFileCreateAccountDto): String {
        return userDomainService.uploadFileCreateAccount(accountDto)
    }

    /**
     * 鉴权
     */
    fun authUser(token: String): UserDto {
        return userDomainService.authUser(token)
    }

    /**
     * 解绑
     */
    fun userUnbindAccount(dto: UnbindAccountDto): UnbindAccountRespDto {
        return userDomainService.userUnbindAccount(dto)
    }

    /**
     * 注册用户
     */
    fun registerUser(openId: String, phone: String): RegisterUserDto {
        return userDomainService.registerUser(openId, phone)
    }

    /**
     * 绑定用户
     */
    fun userBindAccount(dto: BindAccountDto): BindAccountRespDto {
        return userDomainService.userBindAccount(dto)
    }

}