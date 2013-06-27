package com.amm.jsonschema.validator;

import java.util.*;
import java.io.*;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;

public class InvalidTest extends BaseTest {
	private static final Logger logger = Logger.getLogger(InvalidTest.class);

	@DataProvider(name = "invalidFiles") 
	public Object[][] invalidFiles() throws Exception {
		return createRecords(configRecords,"invalid") ;
	}

	@Test(dataProvider = "invalidFiles")
	public void invalid(GenericTestRecord trecord) throws Exception {
		logger.debug("trecord="+trecord);
		testInvalid(trecord.validator,trecord.instanceFile);
	}

}
