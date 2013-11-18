package com.amm.sample.springjdbc.object;

public class Nation extends BaseObject {
	public Nation() {
	}

	public Nation(int id) {
		super(id);
	}

	public Nation(String name) {
		this.name = name ;
	}

	private String name;
	public String getName() { return name; }
	public void setName(String name) { this.name=name; } 
 
	private int population; 
	public int getPopulation() { return population; }
	public void setPopulation(int population) { this.population = population; } 
 
	private Region region; 
	public Region getRegion() { return region; }
	public void setRegion(Region region) { this.region = region; } 
 
	@Override
	public String toString() { 
		return
			"id="+getId()
			+ " name="+name
			+ " population="+population
			+ " region=["+region+"]"
			;
	}
}
