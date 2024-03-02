package com.edu.book.infrastructure.config

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean
import com.edu.book.infrastructure.interceptor.SqlExecuteTimeCountInterceptor
import com.edu.book.infrastructure.interceptor.SqlStatementInterceptor
import org.apache.ibatis.session.SqlSessionFactory
import org.mybatis.spring.SqlSessionTemplate
import org.mybatis.spring.annotation.MapperScan
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.AutoConfigureAfter
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.sql.Connection
import java.sql.SQLException
import javax.sql.DataSource

@Configuration
@AutoConfigureAfter(DataSourceAutoConfiguration::class)
@MapperScan("com.edu.book.infrastructure.repositoryImpl.dao")
@EnableTransactionManagement
class MyBatisConfig {

    @Autowired
    private lateinit var dataSource: DataSource

    @Autowired
    private lateinit var sqlInterceptorConfig: SqlInterceptorConfig

    companion object {
        private val log = LoggerFactory.getLogger(MyBatisConfig::class.java)
    }

    @Bean
    @Throws(SQLException::class)
    fun sqlSessionFactory(): SqlSessionFactory {
        var connection: Connection? = null
        try {
            connection = dataSource.connection
            log.info("----init batis-----")
            val bean = MybatisSqlSessionFactoryBean()
            val resolver = PathMatchingResourcePatternResolver()
            bean.setDataSource(dataSource)
            bean.setMapperLocations(*resolver.getResources("classpath*:mapper/*.xml"), *resolver.getResources("classpath*:mapper/**/*.xml"))
            bean.setPlugins(SqlStatementInterceptor(sqlInterceptorConfig), SqlExecuteTimeCountInterceptor(sqlInterceptorConfig))
            return bean.getObject()!!
        } catch (e: Exception) {
            throw SQLException(e.message)
        } finally {
            connection?.close()
        }
    }

    @Bean
    @ConditionalOnBean(DataSource::class)
    fun transactionManager(): DataSourceTransactionManager {
        return DataSourceTransactionManager(dataSource)
    }

    @Bean
    fun sqlSessionTemplate(sqlSessionFactory: SqlSessionFactory): SqlSessionTemplate {
        return SqlSessionTemplate(sqlSessionFactory)
    }

    @Bean
    fun configurationCustomizer(): ConfigurationCustomizer? {
        return ConfigurationCustomizer { configuration -> configuration.setUseDeprecatedExecutor(false) }
    }

}
