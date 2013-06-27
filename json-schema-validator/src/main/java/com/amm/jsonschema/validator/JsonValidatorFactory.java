package com.amm.jsonschema.validator;

import java.io.File;

/**
 * Create a JsonValidator instance.
 */
public interface JsonValidatorFactory {
	public JsonValidator createInstance(File schemaFile) throws Exception ;
}
