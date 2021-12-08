package com.catane.model;

public enum Resource {

	CLAY, SHEEP, STONE, WHEAT, WOOD;

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}
	
}
