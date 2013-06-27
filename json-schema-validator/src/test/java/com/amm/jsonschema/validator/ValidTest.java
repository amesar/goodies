package com.amm.jsonschema.validator;

import java.util.*;
import java.io.*;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;

public class ValidTest extends BaseTest {
	private static final Logger logger = Logger.getLogger(ValidTest.class);

	@DataProvider(name = "invalidFiles") 
	public Object[][] validFiles() throws Exception {
		return createRecords(configRecords,"valid") ;
	}

	@Test(dataProvider = "validFiles")
	public void valid(GenericTestRecord trecord) throws Exception {
		logger.debug("trecord="+trecord);
		testValid(trecord.validator,trecord.instanceFile);
	}

}
