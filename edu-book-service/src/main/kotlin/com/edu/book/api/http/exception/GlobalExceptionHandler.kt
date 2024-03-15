package com.edu.book.api.http.exception

import com.edu.book.api.vo.exception.ErrorResponse
import com.edu.book.domain.user.exception.AccountBindedException
import com.edu.book.domain.user.exception.AccountNotFoundException
import com.edu.book.domain.user.exception.UserBindedException
import com.edu.book.domain.user.exception.UserException
import com.edu.book.domain.user.exception.UserNotFoundException
import com.edu.book.infrastructure.enums.ErrorCodeConfig
import com.edu.book.infrastructure.exception.WebAppException
import com.edu.book.infrastructure.response.ResponseVo
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.context.request.WebRequest

/**
 * @Author: hongruiming
 * @Date: 2020/11/11 9:29 上午
 * @Description:
 */
@ControllerAdvice
class GlobalExceptionHandler {

    private val log = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    /**
     * 处理通用运行环境的错误
     */
    @ExceptionHandler(value = [Exception::class, RuntimeException::class])
    fun errorHandler(ex: Exception, request: WebRequest): ResponseEntity<ResponseVo<Any?>> {
        log.error("{}", ex)
        val rex = WebAppException(ErrorCodeConfig.SERVER_INTERNAL_ERROR)
        val res = ResponseVo<Any?>(rex)
        return ResponseEntity(res, rex.getHttpStatus())
    }

    /**
     * 处理校验器的错误
     */
    @ExceptionHandler(BindException::class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    fun bindException(ex: BindException): ResponseEntity<ErrorResponse> {
        return logArgumentException(ex)
    }

    /**
     * 处理特殊场景（参数校验）的错误
     */
    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    fun processValidationError(ex: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val errorMsg = ex.bindingResult.fieldError?.defaultMessage
        val webAppException = WebAppException(ErrorCodeConfig.REQUEST_PARAMS_NOT_VALID, errorMsg ?: ex.localizedMessage)
        val res = ErrorResponse(webAppException)
        return ResponseEntity(res, webAppException.getHttpStatus())
    }

    /**
     * 处理web应用的错误
     */
    @ExceptionHandler(WebAppException::class)
    fun handleWebAppException(ex: WebAppException): ResponseEntity<ResponseVo<Any?>> {
        log.warn(ex.toString())
        val res = ResponseVo<Any?>(ex)
        return ResponseEntity(res, ex.getHttpStatus())
    }

    @ExceptionHandler(UserException::class)
    fun handleInteractivityRoomException(ex: UserException): ResponseEntity<ResponseVo<Nothing>> {
        val errorCode = when (ex) {
            is UserNotFoundException -> {
                ErrorCodeConfig.USER_NOT_FOUND
            }
            is UserBindedException -> {
                ErrorCodeConfig.USER_IS_BINDED
            }
            is AccountNotFoundException -> {
                ErrorCodeConfig.ACCOUNT_NOT_FOUND
            }
            is AccountBindedException -> {
                ErrorCodeConfig.ACCOUNT_IS_BINDED
            }
            else -> {
                ErrorCodeConfig.NOT_FOUNT
            }
        }
        log.warn("errorCode is ${errorCode.errorCode}, message = ${ex.message}")
        val res = ResponseVo(errorCode, null)
        return ResponseEntity(res, errorCode.httpStatus)
    }

    private fun logArgumentException(ex: Exception): ResponseEntity<ErrorResponse> {
        val appException = WebAppException(ErrorCodeConfig.REQUEST_PARAMS_NOT_VALID, ex.localizedMessage)
        val res = ErrorResponse(appException)
        return ResponseEntity(res, appException.getHttpStatus())
    }

}