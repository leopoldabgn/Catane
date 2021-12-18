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
		if(hasThief())
			str += " V";
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
	
	public static class Desert extends ResourceCase {

		public Desert() {
			super(-1, Color.WHITE);
		}
		
		@Override
		public void giveResources() {
			
		}
	}
	
	public static class Field extends ResourceCase {

		public Field(int number) {
			super(number, Color.YELLOW);
		}

		@Override
		public void giveResources() {
			
		}
	}
	
	public static class Forest extends ResourceCase {

		public Forest(int number) {
			super(number, new Color(0, 125, 33)); // Dark green*
		}

		@Override
		public void giveResources() {
			
		}
	}
	
	public static class Hill extends ResourceCase {
	
		public Hill(int number) {
			super(number, new Color(8, 255, 0));
		}
	
		@Override
		public void giveResources() {
			
		}
	}
	
	public static class Mountain extends ResourceCase {

		public Mountain(int number) {
			super(number, Color.GRAY);
		}

		public void giveResources() {

		}
	}
	
	public static class Pre extends ResourceCase {
		
		public Pre(int number) {
			super(number, new Color(166, 255, 163)); // Light green
		}

		@Override
		public void giveResources() {
			
		}
	}
	
}
