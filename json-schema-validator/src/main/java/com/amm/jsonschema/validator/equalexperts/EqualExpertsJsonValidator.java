package com.amm.jsonschema.validator.equalexperts;

import java.util.*;
import java.io.*;
import java.net.*;
import org.apache.log4j.Logger;
import com.google.common.io.Files;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import uk.co.o2.json.schema.JsonSchema;
import uk.co.o2.json.schema.ErrorMessage;;
import uk.co.o2.json.schema.SchemaPassThroughCache;;
import com.amm.jsonschema.validator.JsonValidator;

public class EqualExpertsJsonValidator implements JsonValidator {
	private static final Logger logger = Logger.getLogger(EqualExpertsJsonValidator.class);
	private JsonFactory jsonFactory = new JsonFactory(new ObjectMapper());
	private SchemaPassThroughCache schemaFactory = new SchemaPassThroughCache(jsonFactory);
	private JsonSchema schema ;
	private File schemaFile;

	public EqualExpertsJsonValidator(File schemaFile) throws Exception {
		this.schemaFile = schemaFile;
		if (!schemaFile.exists()) 
			throw new Exception("Schema file "+schemaFile+" does not exist");
		URL url = schemaFile.toURL();
		logger.debug("url="+url);
		if (url == null) 
			throw new Exception("Schema file "+schemaFile+" is not URL");
		schema = schemaFactory.getSchema(url);
	}

	public List<String> validate(File instanceFile) throws Exception {
		if (schema == null)
			throw new Exception("No schema");
		if (!instanceFile.exists()) {
			throw new Exception("Instance file "+instanceFile+" does not exist");
		}
		URL url = instanceFile.toURL();
		if (url == null) 
			throw new Exception("Instance file "+instanceFile+" is not URL");

		JsonNode json = jsonFactory.createJsonParser(url).readValueAsTree();
		return validate(json);
	}

	public List<String> validate(String json) throws Exception {
		JsonNode jsonNode = jsonFactory.createJsonParser(json).readValueAsTree();
		return validate(jsonNode) ;
	}

	public List<String> validate(JsonNode json) throws Exception {
		List<ErrorMessage> errors = schema.validate(json);
		List<String> errs = new ArrayList<String>() ;
		for (ErrorMessage error : errors) {
			errs.add(error.toString());
		}
		return errs;
	}

	public String getSchemaName() {
		return schemaFile.getAbsolutePath();
	}

	@Override 
	public String toString() {
		return
			"schemaFile="+schemaFile
			;
	}
}
