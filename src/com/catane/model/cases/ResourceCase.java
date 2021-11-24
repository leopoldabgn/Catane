package com.catane.model.cases;

import java.awt.Color;

import com.catane.model.Board;

public abstract class ResourceCase extends Case {

	private Color color;
	private int number;
	private boolean thief = false;
	
	public ResourceCase(Board board, int number, Color color) {
		super(board);
		this.number = number;
		this.color = color;
	}
	
	public abstract void giveResources();
	
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
