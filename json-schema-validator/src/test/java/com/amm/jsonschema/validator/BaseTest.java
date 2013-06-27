package com.amm.jsonschema.validator;

import com.amm.jsonschema.validator.JsonValidator;
import com.amm.jsonschema.validator.JsonValidatorFactory;
import com.amm.jsonschema.validator.JsonValidatorUtils;
import java.util.*;
import java.io.*;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;
import com.amm.util.CollectionUtils;

public class BaseTest {
	private static final Logger logger = Logger.getLogger(BaseTest.class);
	private static JsonValidatorFactory validatorFactory ;
	static List<GenericConfigRecord> configRecords ;

	private static String [] configFiles = { 
		"appContext.xml",
	} ;

	@BeforeSuite
	public static void beforeSuite() throws Exception {
		initSpring();
	}

	private static void initSpring() {
		ApplicationContext context = new ClassPathXmlApplicationContext(configFiles);
		validatorFactory = context.getBean("validatorFactory",JsonValidatorFactory.class);
		logger.debug("validatorFactory.class="+validatorFactory.getClass().getName());
		logger.debug("validatorFactory=["+validatorFactory+"]");

		configRecords = (List<GenericConfigRecord>)context.getBean("configRecords");
	}

	public void testValid(JsonValidator validator, File instanceFile) throws Exception {
		List<String> errors = validator.validate(instanceFile);
		String emsg = createErrorMessage(validator,errors);
		Assert.assertEquals(errors.size(),0,emsg);
		JsonValidatorUtils.report(instanceFile, errors);
	}

	public void testInvalid(JsonValidator validator,File instanceFile) throws Exception {
		List<String> errors = validator.validate(instanceFile);
		String emsg = createErrorMessage(validator,errors);
		JsonValidatorUtils.report(instanceFile, errors);
		Assert.assertTrue(errors.size() > 0,emsg);
	}

	public Object[][] createRecords(List<GenericConfigRecord> list, String subdir) throws Exception { 
		List<GenericTestRecord> trecords = new ArrayList<GenericTestRecord>();

		for (GenericConfigRecord crecord : list) {
			File dir = new File(crecord.samplesDir , subdir);
			logger.debug("dir="+dir);
			assertDir(dir);
			File [] files = dir.listFiles(new MyFilenameFilter());
			int j=0;
			for (File file : files) {
				GenericTestRecord trecord = new GenericTestRecord();
				trecord.schemaFile =  crecord.schemaFile;
				trecord.instanceFile = file;
				trecord.validator = validatorFactory.createInstance(trecord.schemaFile);
				trecords.add(trecord);
			}
		}
		Object[][] objects = new Object[trecords.size()][1];
		int j=0;
		for (GenericTestRecord trecord : trecords) {
			objects[j++][0] = trecord;
		}
		return objects;
	}

	private String createErrorMessage(JsonValidator validator, List<String> errors) {
		return "SCHEMA: "+validator.getSchemaName()+" ERRORS: "+CollectionUtils.toString(errors);
	}

	private void assertDir(File dir) {
		Assert.assertNotNull(dir,"Directory is null");
		Assert.assertTrue(dir.exists(),"Directory "+dir.getAbsolutePath()+" does not exist");
	}

	private class MyFilenameFilter implements FilenameFilter {
		public boolean accept(File dir, String name) {
			return name.endsWith(".json");
		}
	}
}
