package com.edu.book.infrastructure.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

/**
 * @Auther: liukaihua
 * @Date: 2024/3/11 19:05
 * @Description:
 */

@Configuration
class SystemConfig {

    /**
     * 微信appid
     */
    @Value("\${wechat.appid}")
    var wechatAppId: String = ""

    /**
     * 微信密钥
     */
    @Value("\${wechat.appsecret}")
    var wechatAppSecret: String = ""

    /**
     * 微信域名
     */
    @Value("\${wechat.api.domain}")
    var wechatApiDomain: String = ""

    /**
     * 检黄建暴api
     */
    @Value("\${wechat.api.check}")
    var wechatApiCheckUrl: String = ""

    /**
     * 微信登录api
     */
    @Value("\${wechat.api.login.url}")
    var wechatApiLoginUrl: String = ""

    /**
     * 微信获取tokenurl
     */
    @Value("\${wechat.api.get.token.url}")
    var wechatApiGetTokenUrl: String = ""

    /**
     * 微信获取tokenurl
     */
    @Value("\${wechat.api.get.phone.url}")
    var wechatApiGetPhoneUrl: String = ""

    /**
     * 分布式锁尝试获取时间 ：毫秒
     */
    @Value("\${distributed.lock.wait.time:3000}")
    var distributedLockWaitTime: Long = 3000

    /**
     * 分布式锁释放时间 ： 毫秒
     */
    @Value("\${distributed.lock.release.time:5000}")
    var distributedLockReleaseTime: Long = 5000

    /**
     * isbnappkeuy
     */
    @Value("\${isbn.app.key}")
    var isbnAppKey: String = ""

    /**
     * isbnappsectet
     */
    @Value("\${isbn.app.secret}")
    var isbnAppSecret: String = ""

    /**
     * cpde
     */
    @Value("\${isbn.app.code}")
    var isbnAppCode: String = ""

    /**
     * isbnappsectet
     */
    @Value("\${isbn.host}")
    var isbnHost: String = ""

    /**
     * cpde
     */
    @Value("\${isbn.query.api.url}")
    var isbnQueryApiUrl: String = ""

    /**
     * qiniu
     */
    @Value("\${qiniu.access.key}")
    var qiniuAccessKey: String = ""

    /**
     * qiniu
     */
    @Value("\${qiniu.secret.key}")
    var qiniuSecretKey: String = ""

    /**
     * qiniu
     */
    @Value("\${qiniu.bucket.name}")
    var qiniuBucketName: String = ""

    /**
     * qiniu
     */
    @Value("\${qiniu.bucket.picture.path}")
    var qiniuBucketPicturePath: String = ""

    /**
     * qiniu
     */
    @Value("\${qiniu.bucket.file.path}")
    var qiniuBucketFilePath: String = ""

    /**
     * qiniu
     */
    @Value("\${qiniu.upload.domain}")
    var qiniuUploadDomain: String = ""

    /**
     * 默认头像
     */
    @Value("\${user.default.photo.url:https://www.future-books.fun/Fky1djPcfM3sFQP-BsnPNlLj3In_}")
    var defaultPhotoUrl: String = ""

}