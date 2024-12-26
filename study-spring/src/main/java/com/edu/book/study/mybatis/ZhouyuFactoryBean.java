package com.edu.book.study.mybatis;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.cglib.proxy.Proxy;

import java.lang.reflect.Method;

/**
 * @author 周瑜
 */

public class ZhouyuFactoryBean implements FactoryBean { // zhouyuFactoryBean--->UserMapper代理对象

	private Class mapperInterface;

//	private SqlSession sqlSession;

	public ZhouyuFactoryBean(Class mapperInterface) {
		this.mapperInterface = mapperInterface;
	}

//	@Autowired
//	public void setSqlSession(SqlSessionFactory sqlSessionFactory) {
//		sqlSessionFactory.getConfiguration().addMapper(mapperInterface);
//		this.sqlSession = sqlSessionFactory.openSession();
//	}

	@Override
	public Object getObject() throws Exception {
		Object proxyInstance = Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{mapperInterface}, new InvocationHandler() {
			@Override
			public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
				return null;
			}
		});
		return proxyInstance;
	}

	@Override
	public Class<?> getObjectType() {
		return mapperInterface;
	}
}
