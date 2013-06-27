package com.amm.jsonschema.validator;

import java.util.*;
import java.io.*;
import org.apache.log4j.Logger;
import com.google.common.io.Files;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;

/**
 * JSON validator CLI.
 */
public class JsonValidatorDriver {
	private static final Logger logger = Logger.getLogger(JsonValidatorDriver.class);
	private JsonValidatorFactory validatorFactory;
	private String [] configFiles = { "appContext.xml" } ;

	public static void main(String [] args) throws Exception {
		(new JsonValidatorDriver()).process(args);
	}

	private void process(String [] args)  {
		try {
			processme(args);
		} catch (Exception e) {
			error("OUCH: "+e);
			e.printStackTrace();
		}
		System.exit(0); // FGE hangs if this isn't here :(
	}

	private void processme(String [] args) throws Exception {
		if (args.length < 2) {
			error("Expecting SCHEMA_FILE INSTANCE_FILE");
			return;
		}
		initSpring();
		validate(new File(args[0]), new File(args[1])) ;
	}

	public void validate(File schemaFile, File instanceFile) throws Exception {
		logger.debug("Schema file="+schemaFile);
		logger.debug("Instance file="+instanceFile);

		if (!schemaFile.exists()) {
			throw new IOException("Schema file "+schemaFile+" does not exist");
		}
		JsonValidator validator = validatorFactory.createInstance(schemaFile);
		logger.debug("Validator="+validator);

		if (!instanceFile.exists()) {
			throw new IOException("Instance file "+instanceFile+" does not exist");
		}

		String schema = new String(Files.toByteArray(schemaFile));
		String json = new String(Files.toByteArray(instanceFile));
		List<String> results = validator.validate(instanceFile);
		JsonValidatorUtils.report(instanceFile, results);
	}

	private void initSpring() {
		ApplicationContext context = new ClassPathXmlApplicationContext(configFiles);
		validatorFactory = context.getBean("validatorFactory",JsonValidatorFactory.class);
		logger.debug("validatorFactory.class="+validatorFactory.getClass().getName());
	}
	
	private void error(Object o) { System.out.println("ERROR: "+o);}
}
