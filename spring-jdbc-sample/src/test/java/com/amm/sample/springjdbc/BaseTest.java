package com.amm.sample.springjdbc;

import com.amm.sample.springjdbc.service.*;
import java.util.*;
import java.io.*;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BaseTest {
	private final static Logger logger = Logger.getLogger(BaseTest.class);
	static DomainService serviceService ;

	@BeforeSuite
	public void beforeSuite() {
		initSpring();
		System.out.println(">> beforeSuite");
	}

	private void initSpring() {
		ApplicationContext context = new ClassPathXmlApplicationContext("appContext.xml");
		serviceService = context.getBean("serviceService",DomainService.class);
	}

	void deleteTables() {
		serviceService.deleteNation();
		serviceService.deleteRegion();
	}
}
