package com.amm.sample.springjdbc.object;

import java.util.*;

public class BaseObject {
	private Integer id;
	public Integer getId() { return id; }
	public void setId(Integer id) { this.id=id; } 
 
	public BaseObject() {
	}

	public BaseObject(int id) {
		this.id = id ;
	}

	@Override
	public String toString() {
		return "id="+id ;
	}
}
