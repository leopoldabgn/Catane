package com.catane.model.cases;

import com.catane.model.Player;

public class Road extends MovableCase {
	
	private boolean vertical;
	
	public Road() {
		this(null);
	}
	
	public Road(boolean vertical) {
		this(null, vertical);
	}
	
	public Road(Player player) {
		this(player, false);
	}
	
	public Road(Player player, boolean vertical) {
		super(player);
		this.vertical = vertical;
	}
	
}
