package com.amm.jsonschema.validator;

import java.util.*;
import java.io.*;

public class JsonValidatorUtils {
	
	public static void report(File instanceFile, List<String> array) {
		report(""+instanceFile,array);
	}
	public static void report(String msg, List<String> array) {
		if (0 == array.size()) {
			info("VALID: "+msg);
			//info(msg+" validates");
		} else {
			info("INVALID: "+msg);
			info(array.size()+ " ERRORS: ");
			for (String str : array)
				info("  "+str);
		}
	}

	private static void info(Object o) { System.out.println(o);}
}
