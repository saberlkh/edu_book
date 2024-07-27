package com.edu.book.infrastructure.enums

import com.edu.book.infrastructure.exception.ErrorCode
import org.springframework.http.HttpStatus

/**
 * @Author: hongruiming
 * @Date: 2020/11/10 12:17 下午
 * @Description:
 */
enum class ErrorCodeConfig(override val errorCode: Int,
                           override val errorMessage: String,
                           override val httpStatus: HttpStatus = HttpStatus.OK): ErrorCode {

    INTERNAL_SERVER_ERROR(50000, "Server Internal Error"), //服务器内部错误
    SUCCESS(200200, "请求成功"),
    INVALID_TOKEN(400401, "当前请求需要用户验证，没有权限"),
    PARAMS_NOT_MATCHED(400400, "参数不正确"),
    REQUEST_FAILED(400402, "请求失败"),
    NOT_FOUNT(400404, "对方未在线或帐号不存在"),
    EXCEED_MAX_NUM(400405, "超过房间最大人数"),
    ALREADY_JOIN(400406, "已经加入房间,请勿重复加入"),
    NOT_AS_CREATOR(400407, "非房间主讲人,没有权限"),
    RETAIN_TIME_EXPIRE(400408, "房间超过有效期限,禁止加入"),
    SFU_SERVICE_ERROR(400409, "请求SFU服务失败"),
    REQUEST_PROHIBIT(400410, "请求禁止"),
    GET_ACCESS_TOKEN_FAILED(400411, "获取access_token失败"),
    ZEGO_KICKOUT_FAILED(400412, "请求zego踢人接口失败"),
    DELETE_FAIL(400413, "解散房间失败"),
    DEVICE_ID_LACK(400414, "缺少设备id"),
    VENDOR_NOT_SUPPORTED(400415, "不支持的流媒体服务商"),
    VERSION_IS_TOO_LOW(400416, "客户端版本过低，请升级最新版本"),
    VERSION_IS_TOO_HIGH(400417, "客户端版本过高"),
    NOT_FOUNT_SFU(400418, "未找到对应SFU服务器"),
    NOT_SUPPORT_ACTIVITY_TYPE(400419, "不支持的活动类型"),

    /**
     * 请求参数,未授权等常见异常
     */
    REQUEST_PARAMS_NOT_VALID(400420, "请求参数不合法", HttpStatus.OK), //请求参数非法
    RESOURCE_NOT_FOUND(400421, "请求资源不存在", HttpStatus.OK), // 请求资源不存在
    REQUEST_UNAUTHORIZED(400422, "请求未授权", HttpStatus.OK), //请求未授权
    TOKEN_NOT_SET(400423, "token未设置", HttpStatus.OK), //请求未设置
    QUERY_CLIENT_IN_HIGHER_VERSION_INTERACT(400424, "查询的客户端在更低版本的互动中", HttpStatus.OK),
    INVALID_OPERATION(400910, "Invalid Operation", HttpStatus.OK), //无效操作

    ACTIVITY_TEACHING_NOT_EXIST(400424, "授课端不存在"),
    SERVER_INTERNAL_ERROR(500500, "服务异常"),
    UNKNOWN_ERROR(500501, "未知错误"),

    WECHAT_LOGIN_FAIL(600001, "微信登录失败"),

    WECHAT_GET_PHONE_FAIL(600002, "微信获取手机号失败"),

    TOKEN_EXPIRE(600003, "token失效"),

    USER_NOT_FOUND(600004, "没有找到该用户"),

    USER_IS_BINDED(600005, "用户已经绑定"),

    ACCOUNT_NOT_FOUND(600006, "账号不存在"),

    ACCOUNT_IS_BINDED(600007, "账号已经被绑定"),

    USER_IS_UNBINDED(600008, "用户已经解绑"),

    ISBN_API_FAIL(600009, "通过isbn的api查询图书信息失败"),

    BOOK_EXIST(600010, "图书已经存在"),

    FILE_NAME_NOT_NULL(600011, "文件名不能为空"),

    PASSWORD_ILLEGAL(600012, "密码错误"),

    UPLOAD_MAX_NUM(600013, "上传个数超过最大限制"),

    BOOK_BORROWER(600014, "书籍已经借阅中"),

    GARDEN_ILLDEGA_CAN_NOT_BORROW(600015, "书籍和账号归属不同园区无法进行借阅"),

    GARDEN_UID_NOT_NULL(600016, "园区Id不能为空"),

    BOOK_NOT_COLLECT(600017, "图书还未收藏"),

    BOOK_DETAIL_NOT_EXIST(600018, "图书不存在"),

    BOOK_NOT_BORROWING(600019, "图书没有借阅中"),

    READ_CIRCLE_NOT_FOUND(600020, "阅读圈不存在"),

    BOOK_IN_MENU(600021, "图书已经在书单中"),

    ;

}