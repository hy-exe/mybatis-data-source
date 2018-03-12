package com.mybatis.data.source;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mybatis.data.source.dao.ITestDAO;

public class Booter {

	public static void main(String[] args) {
			String[] sources = new String[] { "spring/applicationContext.xml",
					"spring/applicationContext-persistence.xml", "spring/applicationContext-dao.xml",
					"spring/applicationContext-service.xml" };
			ApplicationContext ctx = new ClassPathXmlApplicationContext(sources);
			ITestDAO testDAO = (ITestDAO) ctx.getBean("testDAO");
			System.out.println(testDAO.getBscInfoEntityListByLac("11"));
			System.out.println("bsc info Server Started... ");
	}

}
