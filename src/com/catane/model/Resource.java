package com.catane.model;

public enum Resource {

	CLAY("Argile"), STONE("Pierre"), WHEAT("Blé"),
	WOOD("Bois"), WOOL("Laine");

	private String name;
	
	private Resource(String name) {
		this.name = name;
	}
	
	public String getEnglishName() {
		return super.toString();
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}
