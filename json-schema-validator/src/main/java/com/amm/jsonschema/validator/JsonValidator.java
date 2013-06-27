package com.amm.jsonschema.validator;

import java.util.*;
import java.io.File;

/**
 * JSON validator wrapper.
 */
public interface JsonValidator {

	public List<String> validate(File instanceFile) throws Exception ;

	public List<String> validate(String json) throws Exception ;

	public String getSchemaName() ;
}
