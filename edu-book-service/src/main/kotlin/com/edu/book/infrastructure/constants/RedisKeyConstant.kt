package com.edu.book.infrastructure.constants

object RedisKeyConstant {

    /**
     * 微信accesstoken
     */
    const val WECHAT_ACCESS_TOKEN_KEY = "WECHAT:ACCESS:TOKEN:"

    /**
     * 注册用户所
     */
    const val REGISTER_USER_LOCK_KEY = "REGISTER:USER:LOCK:"

    /**
     * token
     */
    const val USER_TOKEN_KEY = "USER:TOKEN:"

    /**
     * 超管用户
     */
    const val ADMIN_USER_TOKEN_KEY = "HAIR:ADMIN:USER:TOKEN:"

    /**
     * 绑定解绑
     */
    const val BIND_UNBIND_USER_ACCOUNT_LOCK_KEY = "BIND:UNBIND:USER:ACCOUNT:LOCK:"

    /**
     * isbn扫码
     */
    const val SCAN_ISBN_LOCK_KEY = "SCAN:ISBN:CODE:LOCK:KEY:"

    /**
     * 图书入库
     */
    const val SCAN_BOOK_CODE_KEY = "SCAN:BOOK:CODE:LOCK:KEY:"

    /**
     * 文件上传
     */
    const val UPLOAD_FILE_KEY = "UPLOAD:FILE:KEY:"

}