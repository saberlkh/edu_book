package com.edu.book.infrastructure.exception

/**
 * @Auther: liukaihua
 * @Date: 2024/3/7 10:49
 * @Description:
 */
object ErrorCodeMapUtil {

    const val SUCCESS = "SUCCESS"

    private val hashMap = hashMapOf<String, ErrorCode>(SUCCESS to CommonErrorCode.SUCCESS)

    fun setErrorCode(errorCodeEnumName: String, errorCode: ErrorCode) {
        hashMap[errorCodeEnumName] = errorCode
    }

    fun getErrorCode(errorCodeEnumName: String): ErrorCode {
        return hashMap[errorCodeEnumName]!!
    }

    fun getSuccessErrorCode(): ErrorCode {
        return hashMap[SUCCESS]!!
    }

}