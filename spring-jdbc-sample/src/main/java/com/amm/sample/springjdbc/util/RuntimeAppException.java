package com.amm.sample.springjdbc.util;

public class RuntimeAppException extends RuntimeException {
	public RuntimeAppException() {
	}

	public RuntimeAppException(String message) {
		super(message);
	}

	public RuntimeAppException(Throwable throwable) {
		super(throwable);
	}

	public RuntimeAppException(String message, Throwable throwable) {
		super(message,throwable);
	}
}
