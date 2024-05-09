//package com.edu.book.infrastructure.util
//
//import com.baomidou.mybatisplus.core.mapper.BaseMapper
//import com.baomidou.mybatisplus.generator.FastAutoGenerator
//import com.baomidou.mybatisplus.generator.config.GlobalConfig
//import com.baomidou.mybatisplus.generator.config.OutputFile
//import com.baomidou.mybatisplus.generator.config.PackageConfig
//import com.baomidou.mybatisplus.generator.config.StrategyConfig
//import com.baomidou.mybatisplus.generator.config.TemplateConfig
//import com.baomidou.mybatisplus.generator.config.rules.DateType
//import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy
//import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine
//import com.edu.book.infrastructure.po.BasePo
//import org.springframework.stereotype.Component
//import java.util.Collections
//import java.util.function.Consumer
//
///**
// * @Auther: liukaihua
// * @Date: 2023/2/27 11:46
// * @Description:
// */
//
//fun main(args: Array<String>) {
//    GeneratorCodeUtils().execute(listOf("t_level"))
//}
//
//@Component
//class GeneratorCodeUtils {
//
//    companion object {
//
//        const val ENTITY_TEMPLATE = "/mp-templates/entity.java"
//
//        const val ENTITY_KT_TEMPLATE = "/mp-templates/entity.kt"
//
//        const val CONTROLLER_TEMPLATE = "/mp-templates/controller.java"
//
//        const val MAPPER_TEMPLATE = "/mp-templates/mapper.java"
//
//        const val MAPPER_XML_TEMPLATE = "/mp-templates/mapper.xml"
//
//        const val SERVICE_TEMPLATE = "/mp-templates/service.java"
//
//        const val SERVICE_IMPL_TEMPLATE = "/mp-templates/serviceImpl.java"
//
//        const val DATE_PATTERN = "yyyy-MM-dd HH:mm:ss"
//
//        const val MODULE_NAME = "edu-book-service"
//
//        const val DATASOURCE_URL = "jdbc:mysql://8.138.113.149:3306/edu_book?characterEncoding=UTF-8&rewriteBatchedStatements=true&useSSL=false&allowMultiQueries=true&serverTimezone=Asia/Shanghai"
//
//        const val DATA_USER_NAME = "root"
//
//        const val DATA_PASSWORD = "!@#kdl002841"
//
//    }
//
//    /**
//     * 执行代码
//     */
//    fun execute(tableNameList: List<String>) {
//        FastAutoGenerator.create(DATASOURCE_URL, DATA_USER_NAME, DATA_PASSWORD)
//                .templateConfig(buildTemplateConfig())
//                .globalConfig(buildGlobalConfig())
//                .packageConfig(buildPackageConfig())
//                .strategyConfig(buildStrategyConfig(tableNameList))
//                .templateEngine(VelocityTemplateEngine())
//                .execute()
//    }
//
//    /**
//     * 构建模板配置
//     */
//    fun buildTemplateConfig(): Consumer<TemplateConfig.Builder> {
//        return Consumer {
//            it.entity(ENTITY_TEMPLATE)
//            it.entityKt(ENTITY_KT_TEMPLATE)
//            it.controller(CONTROLLER_TEMPLATE)
//            it.mapper(MAPPER_TEMPLATE)
//            it.mapperXml(MAPPER_XML_TEMPLATE)
//            it.service(SERVICE_TEMPLATE)
//            it.serviceImpl(SERVICE_IMPL_TEMPLATE)
//        }
//    }
//
//    /**
//     * 构建策略配置
//     */
//    fun buildStrategyConfig(tableNameList: List<String>): Consumer<StrategyConfig.Builder> {
//        return Consumer {
//            it.addInclude(tableNameList).addTablePrefix("t_").addFieldPrefix("c_")
//            it.mapperBuilder().superClass(BaseMapper::class.java).formatMapperFileName("%sDao").enableMapperAnnotation().formatXmlFileName("%sDao")
//                    .serviceBuilder().formatServiceFileName("%sRepository").formatServiceImplFileName("%sRepositoryImpl")
//                    .entityBuilder().formatFileName("%sPo").superClass(BasePo::class.java).disableSerialVersionUID().naming(NamingStrategy.underline_to_camel).columnNaming(NamingStrategy.underline_to_camel).enableTableFieldAnnotation()
//                    .controllerBuilder().formatFileName("%sController").enableRestStyle()
//        }
//    }
//
//    /**
//     * 构建公共参数
//     */
//    fun buildGlobalConfig(): Consumer<GlobalConfig.Builder> {
//        val envMap = System.getenv()
//        return Consumer {
//            it.enableKotlin()
//            it.author(envMap.getOrDefault("USERNAME", ""))
//            it.outputDir(System.getProperty("user.dir") + "/" + MODULE_NAME +  "/src/main/kotlin")
//            it.commentDate(DATE_PATTERN)
//            it.dateType(DateType.ONLY_DATE)
//            it.fileOverride()
//            it.disableOpenDir()
//        }
//    }
//
//    /**
//     * 构建包配置
//     */
//    fun buildPackageConfig(): Consumer<PackageConfig.Builder> {
//        return Consumer {
//            it.parent("com")
//            it.moduleName("kindlink")
//            it.entity("po")
//            it.service("repository")
//            it.serviceImpl("repositoryImpl")
//            it.mapper("repositoryImpl.dao")
//            it.xml("mapper")
//            it.controller("controller")
//            it.other("utils")
//            it.pathInfo(Collections.singletonMap(OutputFile.mapperXml, System.getProperty("user.dir") + "/" + MODULE_NAME + "/src/main/resources/mapper"))
//        }
//    }
//
//}