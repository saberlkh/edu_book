package com.edu.book.infrastructure.util

import com.google.common.hash.Hashing
import java.util.concurrent.ConcurrentHashMap
import ma.glasnost.orika.MapperFacade
import ma.glasnost.orika.MapperFactory
import ma.glasnost.orika.converter.BidirectionalConverter
import ma.glasnost.orika.impl.DefaultMapperFactory

/**
 * @Auther: liukaihua
 * @Date: 2024/3/3 14:23
 * @Description:
 */
object MapperUtil {

    /**
     * 默认字段工厂
     */
    private val MAPPER_FACTORY: MapperFactory = DefaultMapperFactory.Builder().build()


    /**
     * 默认字段实例集合
     */
    private val CACHE_MAPPER_FACADE_MAP = ConcurrentHashMap<String, MapperFacade>()


    fun registerConverter(converter: BidirectionalConverter<*, *>) {
        MAPPER_FACTORY.converterFactory.registerConverter(converter.javaClass.simpleName, converter)
    }


    /**
     * 映射实体
     *
     * @param toClass 映射类对象
     * @param data 数据（对象）
     * @param targetPropertyMap 处理字段名称不一致 (key 原对象属性 value 目标对象属性)
     * @param excludes 排除字段数组
     * @return 映射类对象
     */
    fun <E : Any, T : Any> map(
        toClass: Class<E>,
        data: T,
        targetPropertyMap: Map<String, String> = emptyMap(),
        excludes: List<String> = emptyList(),
        converterMap: Map<String, String> = emptyMap()
    ): E {
        val mapperFacade = getMapperFacade(toClass, data::class.java, targetPropertyMap, excludes, converterMap)
        return mapperFacade.map(data, toClass)
    }

    /**
     * 映射集合
     *
     * @param toClass 映射类
     * @param data 数据（集合）
     * @param targetPropertyMap 处理字段名称不一致 (key 原对象属性 value 目标对象属性)
     * @param excludes 排除字段数组
     * @return 映射类对象
     */
    fun <E : Any, T : Any> mapToList(
        toClass: Class<E>,
        data: Collection<T>,
        targetPropertyMap: Map<String, String> = emptyMap(),
        excludes: List<String> = emptyList(),
        converterMap: Map<String, String> = emptyMap()
    ): List<E> {
        if (data.isEmpty()) {
            return emptyList()
        }
        val mapperFacade = getMapperFacade(toClass, data.first()::class.java, targetPropertyMap, excludes, converterMap)
        return mapperFacade.mapAsList(data, toClass)
    }

    /**
     * 将一个对象的属性复制到另外一个对象
     * @param source 源对象
     * @param target 目标对象
     * @param targetPropertyMap 处理字段名称不一致 (key 原对象属性 value 目标对象属性)
     */
    fun <E : Any, T : Any> copy(
        source: E?,
        target: T,
        targetPropertyMap: Map<String, String> = emptyMap(),
        converterMap: Map<String, String> = emptyMap()
    ) {
        if (source == null) {
            return
        }
        val mapKey = source::class.java.canonicalName +
                "_" + target::class.java.canonicalName +
                "_" + murmur3(targetPropertyMap.toString()) +
                "_" + murmur3(converterMap.toString())

        var mapperFacade = CACHE_MAPPER_FACADE_MAP[mapKey]
        if (mapperFacade != null) {
            mapperFacade.map(source, target)
            return
        }

        val classMapBuilder = MAPPER_FACTORY.classMap(source!!::class.java, target::class.java)
        //属性映射
        targetPropertyMap.forEach {
            val converterId = converterMap[it.key]
            if (converterId == null) {
                classMapBuilder.field(it.key, it.value)
            } else {
                classMapBuilder.fieldMap(it.key, it.value).converter(converterId)
            }
        }

        classMapBuilder.byDefault().register()
        mapperFacade = MAPPER_FACTORY.mapperFacade
        CACHE_MAPPER_FACADE_MAP[mapKey] = MAPPER_FACTORY.mapperFacade
        mapperFacade.map(source, target)

    }


    /**
     * 获取自定义映射
     *
     * @param toClass 映射类
     * @param dataClass 数据映射类
     * @param targetPropertyMap 处理字段名称不一致 (key 原对象属性 value 目标对象属性)
     * @return 映射类对象
     */
    private fun <E, T> getMapperFacade(
        toClass: Class<E>,
        dataClass: Class<T>,
        targetPropertyMap: Map<String, String> = emptyMap(),
        excludes: List<String> = emptyList(),
        converterMap: Map<String, String> = emptyMap()
    ): MapperFacade {
        val mapKey = dataClass.canonicalName +
                "_" + toClass.canonicalName +
                "_" + murmur3(targetPropertyMap.toString()) +
                "_" + murmur3(excludes.toString()) +
                "_" + murmur3(converterMap.toString())

        var mapperFacade = CACHE_MAPPER_FACADE_MAP[mapKey]
        if (mapperFacade != null) {
            return mapperFacade
        }

        //排除属性
        var classMapBuilder = MAPPER_FACTORY.classMap(dataClass, toClass)
        excludes.forEach {
            classMapBuilder = classMapBuilder.exclude(it)
        }

        //属性映射
        targetPropertyMap.forEach {
            val converterId = converterMap[it.key]
            if (converterId != null) {
                classMapBuilder.fieldMap(it.key, it.value).converter(converterId).add()
            } else {
                classMapBuilder.field(it.key, it.value)
            }
        }

        classMapBuilder.byDefault().register()
        mapperFacade = MAPPER_FACTORY.mapperFacade
        CACHE_MAPPER_FACADE_MAP[mapKey] = mapperFacade
        return mapperFacade
    }

    private fun murmur3(str: String): Long {
        val murmur = Hashing.murmur3_128().hashString(str, Charsets.UTF_8).asInt()
        return murmur.toLong() + Int.MAX_VALUE.toLong()
    }

}