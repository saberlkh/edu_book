package com.edu.book.api

import com.edu.book.EduBoolServiceApplication
import com.edu.book.application.client.IsbnApi
import com.edu.book.application.client.WechatApi
import com.edu.book.domain.user.dto.CreateAccountDto
import com.edu.book.domain.user.dto.UploadFileCreateAccountDto
import com.edu.book.domain.user.service.UserDomainService
import com.edu.book.infrastructure.config.SystemConfig
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

/**
 * @Auther: liukaihua
 * @Date: 2024/3/13 10:11
 * @Description:
 */

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [EduBoolServiceApplication::class])
class WechatApiTest {

    @Autowired
    private lateinit var wechatApi: WechatApi

    @Autowired
    private lateinit var systemConfig: SystemConfig

    @Autowired
    private lateinit var userDomainService: UserDomainService

    @Autowired
    private lateinit var isbnApi: IsbnApi

    @Test
    fun `创建用户`() {
        val dto = UploadFileCreateAccountDto().apply {
            this.classUid = "38d04e85d27e49afb9c930b119ab5f31"
            this.accountList = listOf(
                CreateAccountDto().apply {
                    this.studentName = "菜虚鲲"
                    this.parentPhone = "13029705528"
                    this.openBorrowService = true
                }
            )
        }
        val result = userDomainService.uploadFileCreateAccount(dto)
        println(result)
    }

    @Test
    fun `查询图书`() {
        println(isbnApi.getBookInfoByIsbnCode("9787510150241"))
    }

    @Test
    fun `注册`() {
//        val result = userDomainService.registerUser("0f3eR6100GlyIR15Pk200IDoN72eR61v")
//        println(JSON.toJSONString(result))
    }

    @Test
    fun `获取accessToken`() {
        val token = wechatApi.getAccessToken(systemConfig.wechatAppId, systemConfig.wechatAppSecret)
        println(token)
        println(wechatApi.getPhone(token, "adwqeqwe123we"))
    }

}