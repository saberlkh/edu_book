package com.study.service

import com.spring.BeanPostProcessor
import com.spring.Component


/**
 * @Author liukaihua
 * @Description //todo
 * @Date 2024/12/15 21:58
 */

@Component
class StudyBeanPostProcessor: BeanPostProcessor {

    override fun postProcessAfterInitialization(beanName: String, bean: Any?): Any? {
        println("调用后置处理器")
        return bean
    }

}