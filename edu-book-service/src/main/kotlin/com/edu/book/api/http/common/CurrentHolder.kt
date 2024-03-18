package com.edu.book.api.http.common

import com.edu.book.domain.user.dto.UserDto

object CurrentHolder {

    private val userDtoThreadLocal = ThreadLocal<UserDto>()

    val userDto: UserDto? get() = userDtoThreadLocal.get()

    fun initUserDtoLocal(userDto: UserDto) {
        userDtoThreadLocal.set(userDto)
    }

    fun clearUserDtoLocal() {
        userDtoThreadLocal.remove()
    }

}