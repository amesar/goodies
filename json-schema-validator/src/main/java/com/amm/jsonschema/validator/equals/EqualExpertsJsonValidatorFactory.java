package com.amm.jsonschema.validator.equalexperts;

import java.util.*;
import java.io.*;
import com.amm.jsonschema.validator.JsonValidator;
import com.amm.jsonschema.validator.JsonValidatorFactory;

public class EqualExpertsJsonValidatorFactory implements JsonValidatorFactory {
	public JsonValidator createInstance(File schemaFile) throws Exception {
		return new EqualExpertsJsonValidator(schemaFile) ;
	}
}
