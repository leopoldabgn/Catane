package com.catane.model;

public enum Resource {

	CLAY("Argile"), SHEEP("Laine"), STONE("Pierre"),
	WHEAT("Blé"), WOOD("Bois");

	private String name;
	
	private Resource(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}
