package com.amm.sample.springjdbc.object;

public class Region extends BaseObject {

	public Region(int id) {
		super(id);
	}

	public Region(String name) {
		this.name = name ;
	}

	private String name;
	public String getName() { return name; }
	public void setName(String name) { this.name=name; } 
 
	private String comment; 
	public String getComment() { return comment; }
	public void setComment(String comment) { this.comment = comment; } 
 
	@Override
	public String toString() { 
		return
			"id="+getId()
			+ " name="+name
			+ " comment="+comment
			;
	}
}
