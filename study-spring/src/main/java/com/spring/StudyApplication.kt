package com.spring

import org.apache.commons.lang3.StringUtils
import java.beans.Introspector
import java.io.File


/**
 * @Author liukaihua
 * @Description //todo
 * @Date 2024/12/15 20:31
 */
class StudyApplication<T> {

    private var appConfig: Class<T>? = null

    private var beanDefinitionMap: MutableMap<String, BeanDefinition> = HashMap()

    private var singletonObjects: MutableMap<String, Any?> = HashMap()

    private var beanPostProcessorList: MutableList<BeanPostProcessor> = ArrayList()

    private fun scanBean(appConfig: Class<T>) {
        if (appConfig.isAnnotationPresent(ComponentScan::class.java)) {
            val componentScan = appConfig.getAnnotation(ComponentScan::class.java)
            val path = componentScan.value
//            println(path)
            val finalPath = path.replace(".", "/")
            val classLoader = this.javaClass.classLoader
            val resource = classLoader.getResource(finalPath)
            val files = File(resource.file)
            if (files.isDirectory) {
                files.listFiles()?.forEach {
                    val absolutePath = it.absolutePath
                    val finalAbsolutePath = absolutePath.substring(absolutePath.indexOf("com"), absolutePath.indexOf(".class"))
                        .replace("/", ".")
                    val clazz = classLoader.loadClass(finalAbsolutePath)
                    if (clazz.isAnnotationPresent(Component::class.java)) {
                        if (BeanPostProcessor::class.java.isAssignableFrom(clazz)) {
                            val instance = clazz.getConstructor().newInstance() as BeanPostProcessor
                            beanPostProcessorList.add(instance)
                        }
                        val componentAnnotation = clazz.getAnnotation(Component::class.java)
                        val beanName = componentAnnotation.value
                        val finalBeanName = if (StringUtils.isBlank(beanName)) {
                            Introspector.decapitalize(clazz.simpleName)
                        } else {
                            beanName
                        }
                        //Bean
//                        println(clazz)
                        //创建bean定义
                        val beanDefinition = BeanDefinition()
                        beanDefinition.type = clazz
                        if (clazz.isAnnotationPresent(Scope::class.java)) {
                            val scopeAnnotation = clazz.getAnnotation(Scope::class.java)
                            val scope = scopeAnnotation.value
                            beanDefinition.scope = scope
                        } else {
                            beanDefinition.scope = "singleton"
                        }
                        beanDefinitionMap.put(finalBeanName, beanDefinition)
                    }
                }
            }
        }
    }

    constructor()

    constructor(appConfig: Class<T>) {
        this.appConfig = appConfig
        //扫描
        scanBean(appConfig)
        beanDefinitionMap.map {
            val beanName = it.key
            val beanDefinition = it.value
            if ("singleton" == beanDefinition.scope) {
                val bean = createBean(beanName, beanDefinition)
                singletonObjects.put(beanName, bean)
            }
        }
    }

    /**
     * 创建bean
     */
    private fun createBean(beanName: String, beanDefinition: BeanDefinition): Any? {
        val clazz = beanDefinition.type
        val instance = clazz?.getConstructor()?.newInstance()
        clazz?.declaredFields?.forEach { field ->
            if (field.isAnnotationPresent(Autowired::class.java)) {
                field.isAccessible = true
                field.set(instance, getBean(field.name))
            }
        }
        if (instance is InitializingBean) {
            instance.afterPropertiesSet()
        }
        beanPostProcessorList.forEach {
            it.postProcessAfterInitialization(beanName, instance)
        }
        return instance
    }

    fun getBean(beanName: String): Any? {
        if (!beanDefinitionMap.containsKey(beanName)) {
            throw RuntimeException("没有找到bean")
        }
        val beanDefinition = beanDefinitionMap.get(beanName) ?: throw RuntimeException("没有找到bean")
        if (StringUtils.equals(beanDefinition.scope, "singleton")) {
            val singletonBean = singletonObjects.get(beanName)
            if (singletonBean != null) return singletonBean
            val newSingletonBean = createBean(beanName, beanDefinition)
            singletonObjects.put(beanName, newSingletonBean)
            return newSingletonBean
        } else {
            return createBean(beanName, beanDefinition)
        }
    }

}