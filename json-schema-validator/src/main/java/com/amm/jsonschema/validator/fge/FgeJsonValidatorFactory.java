package com.amm.jsonschema.validator.fge;

import java.util.*;
import java.io.*;
import com.amm.jsonschema.validator.JsonValidator;
import com.amm.jsonschema.validator.JsonValidatorFactory;

public class FgeJsonValidatorFactory implements JsonValidatorFactory {
	public JsonValidator createInstance(File schemaFile) throws Exception {
		FgeJsonValidator validator = new FgeJsonValidator(schemaFile) ;
		validator.setLogWarnings(logWarnings);
		return validator;
	}

    private boolean logWarnings;
    public boolean getLogWarnings() { return logWarnings; }
    public void setLogWarnings(boolean logWarnings) { this.logWarnings = logWarnings; }
}
