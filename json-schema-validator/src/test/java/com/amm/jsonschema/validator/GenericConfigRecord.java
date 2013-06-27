package com.amm.jsonschema.validator;

import java.util.*;
import java.io.*;

public class GenericConfigRecord {
	public File samplesDir; 
	public File getSamplesDir() { return samplesDir; }
	public void setSamplesDir(File samplesDir) { this.samplesDir = samplesDir; } 

	public File schemaFile; 
	public File getSchemaFile() { return schemaFile; }
	public void setSchemaFile(File schemaFile) { this.schemaFile = schemaFile; } 
 
	@Override
	public String toString() {
		return
			"samplesDir=" + samplesDir 
			+ " schemaFile=" + schemaFile 
		;
	}
}
