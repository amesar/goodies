package com.amm.jsonschema.validator.fge;

import java.util.*;
import java.io.*;
import java.net.*;
import org.apache.log4j.Logger;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.exceptions.ProcessingException;
import com.github.fge.jsonschema.report.ProcessingMessage;
import com.github.fge.jsonschema.report.LogLevel;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.github.fge.jsonschema.report.ProcessingReport;
import com.amm.jsonschema.validator.JsonValidator;

public class FgeJsonValidator implements JsonValidator {
	private static final Logger logger = Logger.getLogger(FgeJsonValidator.class);
	private JsonSchema schema ;
	private File schemaFile;

	public FgeJsonValidator() {
	}

	public FgeJsonValidator(File schemaFile) throws Exception {
		this.schemaFile = schemaFile ;
		logger.info("schema: "+schemaFile);
		if (!schemaFile.exists()) 
			throw new Exception("Schema file "+schemaFile+" does not exist");
		JsonNode nodeSchema = JsonLoader.fromFile(schemaFile) ;
		JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
		schema = factory.getJsonSchema(nodeSchema);
	}

	public List<String> validate(File instanceFile) throws Exception {
		logger.info("INSTANCE: "+instanceFile);
		List<String> list = new ArrayList<String>();
		if (schema == null)
			throw new Exception("No schema");
		if (!instanceFile.exists()) {
			throw new Exception("Instance file "+instanceFile+" does not exist");
		}

		JsonNode instance = JsonLoader.fromFile(instanceFile);

		ProcessingReport report = schema.validate(instance); 
		if (!report.isSuccess()) {
			Iterator<ProcessingMessage> it = report.iterator();
			while (it.hasNext()) {
				ProcessingMessage msg = it.next();
				LogLevel level = msg.getLogLevel();
				if (level == LogLevel.ERROR || level == LogLevel.FATAL || (logWarnings && level == LogLevel.WARNING) )
					list.add(msg.getMessage()+" LEVEL="+level);
			}
		}

		return list;
	}

	public List<String> validate(String json) throws Exception {
		throw new UnsupportedOperationException();
	}

	public String getSchemaName() {
		return schemaFile.getAbsolutePath();
	}
 
	private boolean logWarnings; 
	public boolean getLogWarnings() { return logWarnings; }
	public void setLogWarnings(boolean logWarnings2) { this.logWarnings = logWarnings2; } 

    @Override
    public String toString() {
		return 
    		"schemaFile="+schemaFile
    		+" logWarnings="+logWarnings
		;    
    }
}
