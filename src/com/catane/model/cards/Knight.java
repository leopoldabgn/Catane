package com.catane.model.cards;

public class Knight implements DevelopmentCard {

	private boolean usable;
	
	@Override
	public boolean equals(Object o) {
		if(o == null)
			return false;
		if(!(o instanceof Knight))
			return false;
		return toString().equals(o.toString());
	}
	
	@Override
	public String toString() {
		return "Chevalier";
	}
	
	@Override
	public void canUse() {
		usable = true;
	}

	@Override
	public boolean isUsable() {
		return usable;
	}
	
}
