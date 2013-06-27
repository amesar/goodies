package com.amm.jsonschema.validator;

import com.amm.jsonschema.validator.JsonValidator;
import java.util.*;
import java.io.*;

public class GenericTestRecord {
	public File schemaFile; 
	public File getSchemaFile() { return schemaFile; }
	public void setSchemaFile(File schemaFile) { this.schemaFile = schemaFile; } 
 
	public File instanceFile; 
	public File getInstanceFile() { return instanceFile; }
	public void setInstanceFile(File instanceFile) { this.instanceFile = instanceFile; } 
 
	public JsonValidator validator ;

	@Override
	public String toString() {
		return
			 schemaFile.getName()
			+ ":" + instanceFile.getName()
			 //"SCHEMA=" + schemaFile.getName()
			//+ " INSTANCE=" + instanceFile 
			 //"schemaFile=" + schemaFile.getName()
			//+ " instanceFile=" + instanceFile 
		;
	}
}
