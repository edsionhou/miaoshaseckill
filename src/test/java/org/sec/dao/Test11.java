package org.sec.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.activation.DataSource;

import org.junit.Test;
import org.sec.entity.Seckill;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class Test11 {
	
	
	

	@Test
	public void test1() throws SQLException{
		ApplicationContext ac =  new ClassPathXmlApplicationContext("conf/spring-dao.xml");
			 ComboPooledDataSource bean = ac.getBean("dataSource",ComboPooledDataSource.class);
			 System.out.println(bean);
			 Connection connection = bean.getConnection();
			
	}

	
}
