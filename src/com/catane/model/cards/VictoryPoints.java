package com.catane.model.cards;

public enum VictoryPoints implements DevelopmentCard {

	MARKET_PLACE("Place du marché"), LIBRARY("Bibliothèque"),
	PARLIAMENT("Parlement"), CHURCH("Eglise"), UNIVERSITY("Université");
	
	private String name;
	
	private VictoryPoints(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
	
}
