package com.edu.book.infrastructure.util

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtUpdateWrapper
import org.apache.commons.lang3.ObjectUtils
import kotlin.reflect.KProperty


fun <R, T : Any> KtQueryWrapper<T>.eqIfNotEmpty(column: KProperty<R>, targetValue: Any?): KtQueryWrapper<T> {
    if (ObjectUtils.isNotEmpty(targetValue)) {
        return this.eq(column, targetValue)
    }
    return this
}

fun <R, T : Any> KtQueryWrapper<T>.inIfNotEmpty(column: KProperty<R>, targetValue: List<Any?>?): KtQueryWrapper<T> {
    if (ObjectUtils.isNotEmpty(targetValue)) {
        return this.`in`(column, targetValue)
    }
    return this
}

fun <R, T : Any> KtQueryWrapper<T>.notInIfNotEmpty(column: KProperty<R>, targetValue: List<Any?>?): KtQueryWrapper<T> {
    if (ObjectUtils.isNotEmpty(targetValue)) {
        return this.notIn(column, targetValue)
    }
    return this
}

fun <R, T : Any> KtUpdateWrapper<T>.inIfNotEmpty(column: KProperty<R>, targetValue: List<Any?>?): KtUpdateWrapper<T> {
    if (ObjectUtils.isNotEmpty(targetValue)) {
        return this.`in`(column, targetValue)
    }
    return this
}

/**
 * 小于*
 */
fun <R, T : Any> KtQueryWrapper<T>.ltIfNotEmpty(column: KProperty<R>, targetValue: Any?): KtQueryWrapper<T> {
    if (ObjectUtils.isNotEmpty(targetValue)) {
        return this.lt(column, targetValue)
    }
    return this
}

/**
 * 大于*
 */
fun <R, T : Any> KtQueryWrapper<T>.gtIfNotEmpty(column: KProperty<R>, targetValue: Any?): KtQueryWrapper<T> {
    if (ObjectUtils.isNotEmpty(targetValue)) {
        return this.gt(column, targetValue)
    }
    return this
}

/**
 * 小于等于*
 */
fun <R, T : Any> KtQueryWrapper<T>.leIfNotEmpty(column: KProperty<R>, targetValue: Any?): KtQueryWrapper<T> {
    if (ObjectUtils.isNotEmpty(targetValue)) {
        return this.le(column, targetValue)
    }
    return this
}

/**
 * 大于等于*
 */
fun <R, T : Any> KtQueryWrapper<T>.geIfNotEmpty(column: KProperty<R>, targetValue: Any?): KtQueryWrapper<T> {
    if (ObjectUtils.isNotEmpty(targetValue)) {
        return this.ge(column, targetValue)
    }
    return this
}

fun <R, T : Any> KtUpdateWrapper<T>.notInIfNotEmpty(
    column: KProperty<R>,
    targetValue: List<Any?>?
): KtUpdateWrapper<T> {
    if (ObjectUtils.isNotEmpty(targetValue)) {
        return this.notIn(column, targetValue)
    }
    return this
}

fun <T : Any> KtQueryWrapper<T>.limitOne(): KtQueryWrapper<T> {
    return this.last("LIMIT 1")
}

fun <T : Any> KtQueryWrapper<T>.limit(size: Int): KtQueryWrapper<T> {
    return this.last("LIMIT $size")
}
