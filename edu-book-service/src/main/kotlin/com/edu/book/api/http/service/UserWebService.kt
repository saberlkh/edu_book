package com.edu.book.api.http.service

import com.edu.book.api.vo.user.RegisterUserVo
import com.edu.book.application.service.UserAppService
import com.edu.book.infrastructure.util.MapperUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @Auther: liukaihua
 * @Date: 2024/3/13 23:51
 * @Description:
 */

@Service
class UserWebService {

    @Autowired
    private lateinit var userAppService: UserAppService

    /**
     * 注册用户
     */
    fun registerUser(openId: String): RegisterUserVo {
        val dto = userAppService.registerUser(openId)
        return MapperUtil.map(RegisterUserVo::class.java, dto)
    }

}