package com.catane.model.cards;

public enum Progress implements DevelopmentCard {
	
	ROAD_CONSTRUCTION("Construction de routes"), INVENTION("Invention"),
	MONOPOLY("Monopole");
	
	private String name;
	private boolean usable = false;
	
	private Progress(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
	
	@Override
	public String getEnglishName() {
		return super.toString().toLowerCase();
	}

}
