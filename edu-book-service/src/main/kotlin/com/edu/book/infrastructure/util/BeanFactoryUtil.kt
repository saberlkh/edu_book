package com.edu.book.infrastructure.util

import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component

/**
 * @Auther: liukaihua
 * @Date: 2024/3/2 22:38
 * @Description:
 */

@Component
class BeanFactoryUtil: ApplicationContextAware {

    companion object {
        private lateinit var applicationContext: ApplicationContext

        fun getBean(name: String): Any {
            return getContext().getBean(name)
        }

        fun getBean(clazz: Class<*>): Any {
            return getContext().getBean(clazz)
        }

        fun getBeans(clazz: Class<*>): List<*> {
            return getContext().getBeansOfType(clazz).map { it.value }
        }

        private fun getContext(): ApplicationContext {
            return applicationContext
        }

    }

    override fun setApplicationContext(context: ApplicationContext) {
        applicationContext = context
    }

}