package com.edu.book.api.http.service

import com.edu.book.api.vo.user.BindAccountRespVo
import com.edu.book.api.vo.user.BindAccountVo
import com.edu.book.api.vo.user.RegisterUserVo
import com.edu.book.api.vo.user.UnbindAccountRespVo
import com.edu.book.api.vo.user.UnbindAccountVo
import com.edu.book.application.service.UserAppService
import com.edu.book.domain.user.dto.BindAccountDto
import com.edu.book.domain.user.dto.UnbindAccountDto
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
     * 解绑
     */
    fun userUnbindAccount(vo: UnbindAccountVo): UnbindAccountRespVo {
        val dto = MapperUtil.map(UnbindAccountDto::class.java, vo)
        return MapperUtil.map(UnbindAccountRespVo::class.java, userAppService.userUnbindAccount(dto))
    }

    /**
     * 注册用户
     */
    fun registerUser(openId: String): RegisterUserVo {
        val dto = userAppService.registerUser(openId)
        return MapperUtil.map(RegisterUserVo::class.java, dto)
    }

    /**
     * 绑定
     */
    fun userBindAccount(vo: BindAccountVo): BindAccountRespVo {
        val dto = MapperUtil.map(BindAccountDto::class.java, vo)
        return MapperUtil.map(BindAccountRespVo::class.java, userAppService.userBindAccount(dto))
    }

}