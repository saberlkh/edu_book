package com.edu.book.application.service

import com.edu.book.domain.user.dto.BindAccountDto
import com.edu.book.domain.user.dto.BindAccountRespDto
import com.edu.book.domain.user.dto.RegisterUserDto
import com.edu.book.domain.user.service.UserDomainService
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
     * 注册用户
     */
    fun registerUser(openId: String): RegisterUserDto {
        return userDomainService.registerUser(openId)
    }

    /**
     * 绑定用户
     */
    fun userBindAccount(dto: BindAccountDto): BindAccountRespDto {
        return userDomainService.userBindAccount(dto)
    }

}