package com.catane.model.cards;

public enum Progress implements DevelopmentCard {
	
	ROAD_CONSTRUCTION("Construction de routes"), INVENTION("Invention"),
	MONOPOLY("Monopole");
	
	private String name;
	private boolean usable;
	
	private Progress(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
	
	public void canUse() {
		usable = true;
	}

	public boolean isUsable() {
		return usable;
	}

}
