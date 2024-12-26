package com.edu.book.study.config

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.EnableAspectJAutoProxy

/**
 * @Classname StudyConfig
 * @Description TODO
 * @Date 2024/12/24 20:57
 * @Created by liukaihua
 */

@ComponentScan("com.edu.book.study")
@EnableAspectJAutoProxy
class StudyConfig {

    //    @Bean
    //    @Throws(IOException::class)
    //    fun sqlSessionFactory(): SqlSessionFactory {
    //        val inputStream = Resources.getResourceAsStream("mybatis.xml")
    //        val sqlSessionFactory = SqlSessionFactoryBuilder().build(inputStream)
    //        return sqlSessionFactory
    //    }



//	@Bean
    //	public JdbcTemplate jdbcTemplate() {
    //		return new JdbcTemplate(dataSource());
    //	}
    //
    //	@Bean
    //	public PlatformTransactionManager transactionManager() {
    //		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
    //		transactionManager.setDataSource(dataSource());
    //		return transactionManager;
    //	}
    //
    //	@Bean
    //	public DataSource dataSource() {
    //		DriverManagerDataSource dataSource = new DriverManagerDataSource();
    //		dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/tuling?characterEncoding=utf-8&amp;useSSL=false");
    //		dataSource.setUsername("root");
    //		dataSource.setPassword("Zhouyu123456***");
    //		return dataSource;
    //	}
    //	@Bean
    //	public MapperScannerConfigurer configurer(){
    //		MapperScannerConfigurer configurer = new MapperScannerConfigurer();
    //		configurer.setBasePackage("com.zhouyu.mapper");
    //
    //		return configurer;
    //	}
    //	@Bean
    //	public SqlSessionFactory sqlSessionFactory() throws Exception {
    //		SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
    //		sessionFactoryBean.setDataSource(dataSource());
    //		return sessionFactoryBean.getObject();
    //	}


}