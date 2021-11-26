package com.catane.model.cases;

import java.awt.Color;

public abstract class ResourceCase extends Case {

	private Color color;
	private int number;
	private boolean thief = false;
	
	public ResourceCase(int number, Color color) {
		this.number = number;
		this.color = color;
	}
	
	public abstract void giveResources();
	
	@Override
	public String toString() {
		String str = getClass().getSimpleName();
		if(!(this instanceof Desert))
			str += " "+number;
		int max = 13;
		if(str.length() >= max)
			return str;
		int reste = max-str.length();
		str = (reste % 2 == 1 ? " " : "")+" ".repeat(reste/2)+str+" ".repeat(reste/2);

		return str;
	} 
	
	public int getNumber() {
		return number;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setThief(boolean thief) {
		this.thief = thief;
	}
	
	public boolean hasThief() {
		return thief;
	}
	
}
