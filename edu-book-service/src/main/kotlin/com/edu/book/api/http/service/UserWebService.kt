package com.edu.book.api.http.service

import com.edu.book.api.vo.user.BindAccountRespVo
import com.edu.book.api.vo.user.BindAccountVo
import com.edu.book.api.vo.user.CreateAccountRespVo
import com.edu.book.api.vo.user.PageQueryAccountParamVo
import com.edu.book.api.vo.user.PageQueryAccountVo
import com.edu.book.api.vo.user.RegisterUserVo
import com.edu.book.api.vo.user.UnbindAccountRespVo
import com.edu.book.api.vo.user.UnbindAccountVo
import com.edu.book.api.vo.user.UploadFileCreateAccountVo
import com.edu.book.application.service.UserAppService
import com.edu.book.domain.user.dto.BindAccountDto
import com.edu.book.domain.user.dto.CreateAccountDto
import com.edu.book.domain.user.dto.PageQueryAccountParamDto
import com.edu.book.domain.user.dto.UnbindAccountDto
import com.edu.book.domain.user.dto.UploadFileCreateAccountDto
import com.edu.book.infrastructure.util.ExcelUtils
import com.edu.book.infrastructure.util.MapperUtil
import com.edu.book.infrastructure.util.page.Page
import org.apache.commons.lang3.StringUtils
import org.apache.commons.lang3.math.NumberUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

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
     * 分页查询
     */
    fun pageQueryAccountListByClass(param: PageQueryAccountParamVo): Page<PageQueryAccountVo> {
        val paramDto = MapperUtil.map(PageQueryAccountParamDto::class.java, param)
        val pageQuery = userAppService.pageQueryAccountListByClass(paramDto)
        if (pageQuery.result.isNullOrEmpty()) return Page()
        return Page(param.page, param.pageSize, pageQuery.totalCount, MapperUtil.mapToList(PageQueryAccountVo::class.java, pageQuery.result!!))
    }

    /**
     * 生成账号
     */
    fun uploadFileCreateAccount(file: MultipartFile, classUid: String): CreateAccountRespVo {
        val fileDatas = ExcelUtils.importData(file, NumberUtils.INTEGER_ONE, UploadFileCreateAccountVo::class.java)
        val accountDto = UploadFileCreateAccountDto().apply {
            this.classUid = classUid
            this.accountList = fileDatas.map {
                CreateAccountDto().apply {
                    this.studentName = it.studentName
                    this.openBorrowService = StringUtils.equals(it.openBorrowService, "是")
                    this.parentPhone = it.parentPhone
                }
            }
        }
        return CreateAccountRespVo().apply {
            this.downloadUrl = userAppService.uploadFileCreateAccount(accountDto)
        }
    }

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