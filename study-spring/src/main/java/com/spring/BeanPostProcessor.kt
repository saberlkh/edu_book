package com.spring

/**
 * @Author liukaihua
 * @Description //todo
 * @Date 2024/12/15 21:57
 */
interface BeanPostProcessor {

    fun postProcessBeforeInitialization(beanName: String, bean: Any?): Any? {
        return bean
    }

    fun postProcessAfterInitialization(beanName: String, bean: Any?): Any? {
        return bean
    }

}
