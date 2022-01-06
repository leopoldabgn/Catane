package com.catane.model.cases;

import java.awt.Color;
import java.util.List;

import com.catane.model.Resource;

public class ResourceCase extends Case {

	private Color color;
	private int number;
	private boolean thief = false;
	
	public ResourceCase(int number, Color color) {
		this.number = number;
		this.color = color;
	}
	
	public void giveResources(List<Colony> col) {}
	
	@Override
	public String toString() {
		String str = "";
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
		public String toString() {
			return "Désert" + super.toString();
		}
	}
	
	public static class Field extends ResourceCase {

		public Field(int number) {
			super(number, Color.YELLOW);
		}

		@Override
		public void giveResources(List<Colony> col) {
			for (Colony colony : col)
				for (int i = 0; i < colony.resourceGain(); i++)
					colony.getPlayer().gainResource(Resource.WHEAT);
		}
		
		@Override
		public String toString() {
			return "Champ" + super.toString();
		}
	}
	
	public static class Forest extends ResourceCase {

		public Forest(int number) {
			super(number, new Color(0, 125, 33)); // Dark green*
		}

		@Override
		public void giveResources(List<Colony> col) {
			for (Colony colony : col)
				for (int i = 0; i < colony.resourceGain(); i++)
					colony.getPlayer().gainResource(Resource.WOOD);
		}
		
		@Override
		public String toString() {
			return "Forêt" + super.toString();
		}
	}
	
	public static class Hill extends ResourceCase {
	
		public Hill(int number) {
			super(number, new Color(8, 255, 0));
		}
	
		@Override
		public void giveResources(List<Colony> col) {
			for (Colony colony : col)
				for (int i = 0; i < colony.resourceGain(); i++)
					colony.getPlayer().gainResource(Resource.CLAY);
		}
		
		@Override
		public String toString() {
			return "Colline" + super.toString();
		}
	}
	
	public static class Mountain extends ResourceCase {

		public Mountain(int number) {
			super(number, Color.GRAY);
		}

		@Override
		public void giveResources(List<Colony> col) {
			for (Colony colony : col)
				for (int i = 0; i < colony.resourceGain(); i++)
					colony.getPlayer().gainResource(Resource.STONE);
		}
		
		@Override
		public String toString() {
			return "Montagne" + super.toString();
		}
	}
	
	public static class Pre extends ResourceCase {
		
		public Pre(int number) {
			super(number, new Color(166, 255, 163)); // Light green
		}

		@Override
		public void giveResources(List<Colony> col) {
			for (Colony colony : col)
				for (int i = 0; i < colony.resourceGain(); i++)
					colony.getPlayer().gainResource(Resource.WOOL);
		}
		
		@Override
		public String toString() {
			return "Pré" + super.toString();
		}
	}
	
}
